#include<stdio.h>
#include<conio.h>
int main ()
{
int a[10][10];
int m,n;
int i,j;
printf ("nhap so cot so dong");
scanf ("%d%d",&m,&n);
for(i=0;i<m;i++)
for(j=0;j<n;j++){
printf("a[%d,%d]=",i,j);
scanf("%f",a[i][j]);
}
}
