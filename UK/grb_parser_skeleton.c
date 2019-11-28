#include <stdio.h>
#include <string.h>   /* for all the new-fangled string functions */
#include <stdlib.h>     /* malloc, free, rand */
 
int Fsize=50; /* max string length of formulas*/
int inputs=10;
 
/*put all your functions here.  You will need
1.
int parse(char *g) which returns 1 if a proposition, 2 if neg, 3 if binary, ow 0
Of course you will almost certainly need other functions.
 
For binary formulas you will also need functions that return the first part and the second part of the binary formula.
 
char *partone(char *g)
 
char *parttwo(char *g)
*/
int notOk;
 
void prop(char *g,int *i)
{
    if(strchr("pqr", g[*i])){
        *i+=1;
    }
    else{
        notOk=1;
    }
}
 
void BC(char *g, int *i)
{
    if(strchr("v^>", g[*i])){
        *i+=1;
    }
    else{
        notOk=1;
    }
}
 
void fmla(char *g, int *i)
{
    if(g[*i]=='-')
    {
 
        *i+=1;
        fmla(g,i);
    }
    else if(g[*i]=='(')
    {
        *i+=1;
        fmla(g,i);
        BC(g,i);
        fmla(g,i);
 
        if(g[*i]==')'){
            *i+=1;
        }
        else {
            notOk=1;
        }
    }
    else prop(g,i);
}
 
int isFormula(char*g){
 
    int i = 0;
    notOk = 0;
    fmla(g,&i);
    return(i == strlen(g) && notOk == 0);
}
int parse(char *g)
{
    int formulaVal=isFormula(g);
 
    if(formulaVal)
        if(g[0]=='-')
            return(2);
        else
            return(3);
    else return formulaVal;
}
 
int parenCount[];
 
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
 
}
char *partone(char*g)
{
    int sizeOfWord = strlen(g);
    int i;
    char* part1 = malloc(Fsize);
 
    for(i = 0; i < sizeOfWord; i++)
    {
        if(strchr("v^>", g[i]) && parenCount[i]==1)
        {
            for(int j = 1; j<i;j++)
            {
                part1[j-1]=g[j];
            }
            part1[i-1]='\0';
           /*strncpy(part1, g + 1, i - 1);
            if((strlen(part1)) %2==0) {
                part1[strlen(part1)-1]='\0';
            }
            //else part1[strlen(part1)]='\0';*/
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
            for(int j=i+1;j<sizeOfWord-1;j++)
            {
                part2[j-i-1]=g[j];
                len++;
            }
            part2[len]='\0';
            /*strncpy(part2,(g+i+1),(sizeOfWord-i-2));
            if((sizeOfWord-i-2)%2==0) {
                part2[strlen(part2)-1]='\0';
               // strncpy(part2,(g+i+1),(sizeOfWord-i-2));
               
            }
            else
            {
               // part2[strlen(part2)]='\0';
            }
            */
           
            break;
        }
 
    }
    return part2;
}
int main(){ /*input 10 strings from "input.txt" */
 
    /*parenCounter("((p^q)^(qvr))");
    printf(partone("((p^q)^(qvr))"));
    printf("\n");
    printf(parttwo("((p^q)^(qvr))"));*/
 
    /*
     * prop ::= p|q|r.
       BC ::= v | b | >
       .
        ::= prop | − fmla | (fmla BC fmla).
     * */
 
 
    /* reads from input.txt, writes to output.txt*/
    char *name = malloc(Fsize);
    FILE *fp, *fpout;
    if ((  fp=fopen("input.txt","r"))==NULL){printf("Error opening file");exit(1);}
 
    if ((  fpout=fopen("output.txt","w"))==NULL){printf("Error opening file");exit(1);}
 
    int j;
    for(j=0;j<inputs;j++)
    {
        fscanf(fp, "%s",name);//read formula
        printf("%s\n",name);
        switch (parse(name))
        {
            case(0): fprintf(fpout, "%s is not a formula.  \n", name);break;
            case(1): fprintf(fpout, "%s is a proposition. \n ", name);break;
            case(2): fprintf(fpout, "%s is a negation.  \n", name);break;
            case(3):parenCounter(name);fprintf(fpout, "%s is a binary formula. The first part is %s and the second part is %s  \n", name,partone(name),parttwo(name) );break;
            default:fprintf(fpout, "What the f***!  ");
       }
    }
 
 
 
    fclose(fp);
    fclose(fpout);
    free(name);
 
 
return(0);
}