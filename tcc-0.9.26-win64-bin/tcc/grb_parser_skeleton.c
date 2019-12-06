/*
bug :
	name = p  输出 :p is a binary formula. The first part is  and the second part is 不正确
	name = pvr 输出 :pvr is a binary formula. The first part is  and the second part is 不正确
*/
#include <stdio.h>
#include <string.h>   /* for all the new-fangled string functions */
#include <stdlib.h>     /* malloc, free, rand */
 
int Fsize=50; /* max string length of formulas*/
//int inputs=10;
int inputs=1;  // grb
int gNotOk;
 
int gRecursionDepth = 0 ;   // 递归深度

int DEBUG = 1;	

void printRecursionInfo(char * funName,char c){
    if(DEBUG){
        int j = 0;
        for(j = 0 ;j <= gRecursionDepth ;j++){
            printf("  ");
        }
        printf("        call %s('%c')  gRecursionDepth = %d  \n",funName,c,gRecursionDepth);
    }
}
/************************************************************************
breif		: 	判断 g[i] 是否为pqr中其中某一个
intput		:	g  ：[string]	 propositional logic 字符串
				i  : [int]		 第i个字符
returns 	:	无    

note		:	如果g[i]不是pqr之一,则可以判断不是propositional formula 
				如果g[i]是pqr之一,则对i所在的地址的值加1
*************************************************************************/

void prop(char *g,int *i)
{
    printRecursionInfo("prop",g[*i]);   // rbg added for debug 
    if(strchr("pqr", g[*i])){
        *i+=1;
    }
    else{
        gNotOk=1;
    }
}

/* 参考prop注释 */
void BC(char *g, int *i)
{
    printRecursionInfo("BC",g[*i]); // rbg added for debug 
    if(strchr("v^>", g[*i])){
        *i+=1;
    }
    else{
        gNotOk=1;
    }
}

/************************************************************************
breif		: 	判断g[i:-1]是否为 propositional formula 
intput		:	g  ：[string]	 propositional logic 字符串
				i  : [int]		 第i个字符 注意传入参数为地址
returns 	:	无    

note		:	
                1. 虽然无返回值,但却对全局变量gNotOk进行操作了 , gNotOk标志是否为propositional logic
*************************************************************************/

void fmla(char *g, int *i)
{	
	if(DEBUG){
		int j = 0;
        gRecursionDepth += 1;
		for(j = 0 ;j <= gRecursionDepth ;j++){
			printf("  ");
		}
        
		printf("------ fmla  begin ---------  ");
        printf("gRecursionDepth = %d  ",gRecursionDepth);
		printf("g[%d:%d] = %s , *i = %d \n",*i,strlen(g)-1,g+*i,*i);
		
	}

    // switch (expression)
    // {
    // case /* constant-expression */:
    //     /* code */
    //     break;
    
    // default:
    //     break;
    // }
    
    if(g[*i]=='-'){		//  第一个字符为负号 递归
        *i+=1;
        fmla(g,i);
    }
    else if(g[*i]=='(') // 第一个字符为左括号 递归
    {
        *i+=1;
        fmla(g,i);
        BC(g,i);
        fmla(g,i);
        if(g[*i]==')'){
            *i+=1;
        }
        else {
            gNotOk=1;
        }
    }
    else {  // 第一个字符既不为负号，也不为括号, 则必须是pqr之一
        prop(g,i);
    }

	if(DEBUG){
		int j = 0;
		for(j = 0 ;j <= gRecursionDepth ;j++){
			printf("  ");
		}
		printf("------ fmla  end   ---------  ");
        printf("gRecursionDepth = %d  \n",gRecursionDepth);
        gRecursionDepth -= 1 ;
	}
}

/************************************************************************
breif		: 	判断是否为命题公式(不细分是negation还是binary formula)
intput		:	char* 	g  	 待判断的命题逻辑字符串
returns 	:	bool	            
*************************************************************************/

int isFormula(char*g){
 
    int i = 0;
    gNotOk = 0;
    fmla(g,&i);
	printf("strlen(g) = %d  ",strlen(g));
    //return(i == strlen(g) && gNotOk == 0);
	return gNotOk == 0;
}

/************************************************************************
breif		: 	命题公式的类型(细分是negation还是binary formula)
intput		:	char* 	g  	 待判断的命题逻辑字符串
returns 	:	int	            
				0	not a formula
            	1	proposition	formula
            	2	negation formula
            	3.	binary formula
*************************************************************************/
int parse(char *g)
{
    int formulaVal = isFormula(g);
    if(formulaVal)	
        if(g[0]=='-')
            return(2);
        else
            return(3);
    else 
		return formulaVal;
}
 
int parenCount[100];
 
/*
    brief :     为parenCount赋值
 */
void parenCounter (char*g)
{
    int sizeOfWord=strlen(g);
    char *copy = malloc(Fsize);
    strcpy(copy, g);
    parenCount[0]=1;
    int i;
    for(i=1;i<sizeOfWord;i++)
    {
        int prevValue = parenCount[i-1];
        if(g[i]=='(') {
            parenCount[i] = prevValue + 1;
         
        }
        else if (g[i]==')') {
            parenCount[i] = prevValue - 1;
       
        }
        else
            parenCount[i] = prevValue;
    }
    for(i=0;i<sizeOfWord;i++)
    {
        printf("parenCount[i] = %d \n",parenCount[i]);
    }
 
}
char *partone(char*g)
{
    int sizeOfWord = strlen(g);
    int i;
    char* part1 = malloc(Fsize);
 
    for(i = 0; i < sizeOfWord; i++)
    {
        if(strchr("v^>", g[i]) && parenCount[i]==1)     // parenCount[i]==1 是 part1 和 part2的分界点
        {
	        int j ;
            for(j = 1; j<i;j++)
            {
                part1[j-1]=g[j];
            }
            part1[i-1]='\0';
            break;
        }
    }
    return part1;
}
char *parttwo(char *g)
{
    int sizeOfWord = strlen(g);
    int i;
    char* part2 = malloc(Fsize);
    for(i = 0; i < sizeOfWord; i++)
    {
        if(strchr("v^>", g[i]) && parenCount[i] == 1)
        {
            int len=0;
	        int j = 0;
            for(j=i+1;j<sizeOfWord-1;j++)
            {
                part2[j-i-1]=g[j];
                len++;
            }
            part2[len]='\0';
           
            break;
        }
 
    }
    return part2;
}
int main(){ /*input 10 strings from "input.txt" */
 
    /* reads from input.txt, writes to output.txt*/
    // char *name = malloc(Fsize);
    char *name = "-(p^q^r)";
    // FILE *fp, *fpout;
    // if ((  fp=fopen("input.txt","r"))==NULL){printf("Error opening file");exit(1);}
 
    // if ((  fpout=fopen("output.txt","w"))==NULL){printf("Error opening file");exit(1);}
 
    int j;
    for(j=0;j<inputs;j++)
    {
        // fscanf(fp, "%s",name);//read formula
        printf("name  == %s \n ",name);
		int rt = parse(name);
		printf("rt = %d  ",rt);
        switch (rt)
        {
            case(0): 
				printf("%s is not a formula.  \n\n\n", name);
				// fprintf(fpout, "%s is not a formula.  \n", name);
				break;
            case(1): 
				printf("%s is a proposition. \n\n\n", name);
				// fprintf(fpout, "%s is a proposition. \n ", name);
				break;
            case(2): 
				printf("%s is a negation.  \n\n\n", name);
				// fprintf(fpout, "%s is a negation.  \n", name);
				break;
            case(3):
				parenCounter(name);
                //int len = sizeof(parenCount) /sizeof(int);
                //printf("len = %d",len);
				printf("%s is a binary formula. The first part is %s and the second part is %s  \n\n\n", name,partone(name),parttwo(name) );
				// fprintf(fpout, "%s is a binary formula. The first part is %s and the second part is %s  \n", name,partone(name),parttwo(name) );
				break;
            default:
                printf("What the f***! ");
				// fprintf(fpout, "What the f***!  ");
       }
    }
    // fclose(fp);
    // fclose(fpout);
    // free(name);
 
 
return(0);
}
