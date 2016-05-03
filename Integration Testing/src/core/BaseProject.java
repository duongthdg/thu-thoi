package core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.expression.FunctionCallExpression;
import core.models.FunctionTestResult;
import core.solver.Solution;
import core.solver.SymbolicExecutor;
import core.solver.random.RandomSolver;
import core.solver.z3.Z3Solver;
import api.IProject;
import api.models.ITestpath;
import api.models.ICFG;
import api.models.IFunction;
import api.models.IStatement;
import api.models.IFunctionTestResult;
import api.models.IType;
import api.models.IVariable;
import api.parser.ISymbolicExecutor;
import api.solver.IPathConstraints;
import api.solver.ISolution;
import api.solver.ISolver;

public abstract class BaseProject implements IProject {
	
	private List<IFunction> listFunction;
	private List<IVariable> listGlobalVar;
	private List<IType> listType;
	private File root;

	protected BaseProject(File root){
		listFunction = new ArrayList<>();
		listGlobalVar = new ArrayList<>();
		listType = new ArrayList<>();
		this.root = root;
		
		loadProject();
	}
	
	@Override
	public File getRoot() {
		return root;
	}

	protected void loadProject() {
		
		//Lọc các hàm từ các tập tin mã nguồn
		parseEachSource(root);
		
	}
	
	private void parseEachSource(File parent){
		for (File f: parent.listFiles()){
			if (f.isDirectory())
				parseEachSource(f);
			else if (accept(f)) {
				getProjectParser().parseSource(f, this);
			}
		}
	}

	@Override
	public IFunctionTestResult testFunction(IFunction func) throws InterruptedException {
		ICFG cfg_12 = func.getCFG(ICFG.COVER_BRANCH),
			cfg_3 = func.getCFG(ICFG.COVER_SUBCONDITION);
		List<ITestpath> allPath_12 = cfg_12.getAllBasisPaths(),
				coverPath_3 = cfg_3.getCoverBranchPaths(),
				errorPath = new ArrayList<>();
		IVariable[] params = func.getParameters();
		checkStop();
		
		//Reset visit state
		for (IStatement stm: cfg_12.getStatements())
			stm.setVisit(false);
		for (IStatement stm: cfg_3.getStatements())
			stm.setVisit(false);
		
		//Phân tích và giải các ràng buộc phủ câu lệnh/nhánh
		for (ITestpath path: allPath_12){
			checkStop();
			List<IPathConstraints> cnts = getConstraintParser()
					.execPath(path, params, ISymbolicExecutor.PARSE_ERROR_PATH);
			
			for (IPathConstraints cnt: cnts){
				//System.out.println("\nHe rang buoc: " + cnt);
				checkStop();
				ISolution r = solveConstraintByList(cnt);
				cnt.getPath().setSolution(r);
				//System.out.println("=> Ket qua: " + r);
				
				if (cnt.getConstraintType() != IPathConstraints.TYPE_NORMAL)
					errorPath.add(cnt.getPath());
			}
		}
		
		//Phân tích và giải các ràng buộc phủ điều kiện con
		for (ITestpath path: coverPath_3){
			IPathConstraints cnt = getConstraintParser()
					.execPath(path, params, ISymbolicExecutor.DEFAULT).get(0);
			checkStop();
			ISolution r = solveConstraintByList(cnt);
			path.setSolution(r);
		}
		
		//TODO phân tích và giải các ràng buộc cho vòng lặp
		
		errorPath.removeIf(p -> p.getSolution().getCode() != ISolution.ERROR);
		
		Map<Integer, List<ITestpath>> result = new HashMap<>();
		result.put(IFunctionTestResult.STATEMENT, cfg_12.getCoverStatementPaths());
		result.put(IFunctionTestResult.BRANCH, cfg_12.getCoverBranchPaths());
		result.put(IFunctionTestResult.SUBCONDITION, coverPath_3);
		result.put(IFunctionTestResult.ALLPATH, allPath_12);
		result.put(IFunctionTestResult.ERROR, errorPath);
		FunctionTestResult r = new FunctionTestResult(result);
		
		func.setTestResult(r);
		return r;
	}


	/**
	 * Tìm hàm số trong danh sách khai báo khớp với một lời gọi hàm
	 * @param call lời gọi hàm, thí dụ: test(x, y)
	 * @return hàm số tương thích với với lời gọi
	 * @throws RuntimeException không tìm được khai báo khớp lời gọi ham
	 */
	protected IFunction findFunctionByCall(FunctionCallExpression call) 
			throws RuntimeException{
		for (IFunction func: listFunction){
			if (isMatchFunctionCall(func, call))
				return func;
		}
		throw new RuntimeException("Function not found");
	}
	
	/**
	 * Kiểm tra một lời gọi hàm có khớp với một hàm số
	 */
	private static boolean isMatchFunctionCall(IFunction func, 
			FunctionCallExpression call){
		//Lọc ra hàm khớp tên lời gọi
		if (!func.getName().equals(call.getName()))
			return false;
		
		//Lọc ra hàm có số lượng đối số bằng số lượng tham số gọi 
		if (func.getParameters().length != call.getArguments().length)
			return false;
		
		return true;
	}

	@Override
	public List<IFunction> getFunctions() {
		return listFunction;
	}

	@Override
	public void addFunction(IFunction function) {
		listFunction.add(function);
	}
	
	@Override
	public List<IVariable> getGlobalVars() {
		return listGlobalVar;
	}

	@Override
	public void addGlobalVar(IVariable global) {
		listGlobalVar.add(global);
	}
	
	@Override
	public List<IType> getLoadedType() {
		return listType;
	}

	@Override
	public void addLoadedType(IType type) {
		listType.add(type);
	}
	
	@Override
	public ISymbolicExecutor getConstraintParser() {
		return new SymbolicExecutor();
	}
	
	private static final String Z3 = "Z3", RANDOM = "Random";
	public static final String[] BASE_LIST_SOLVER = {Z3, RANDOM};
	
	
	@Override
	public List<ISolver> getListSolver() {
		List<ISolver> list_solver = new ArrayList<>(Config.LIST_SOLVER.length);
			for (String solver: Config.LIST_SOLVER)
				switch (solver){
				case Z3:
					list_solver.add(new Z3Solver());
					break;
				case RANDOM:
					list_solver.add(new RandomSolver());
					break;
				}
		
		return list_solver;
	}
	
	protected ISolution solveConstraintByList(IPathConstraints cnt){
		Exception e = null;
		
		for (ISolver solver: getListSolver()){
			try {
				ISolution r = solver.solveConstraint(cnt);
				
				if (r.getCode() != ISolution.UNKNOWN)
					return r;
			} catch (Exception ex){
				e = ex;
			}
		}
		
		if (e != null)
			throw new RuntimeException(e);
		return Solution.DEFAULT;
	}

}
