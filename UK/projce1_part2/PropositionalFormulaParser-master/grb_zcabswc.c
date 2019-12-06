#include <stdio.h> 
#include <string.h>   /* for all the new-fangled string functions */
#include <stdlib.h>     /* malloc, free, rand */

int Fsize=50;
int cases=10;
/*project1-part1 begin */
int Fsize=50; /* max string length of formulas*/
//int inputs=10;
int inputs=1;  // grb
int gNotOk;  // 标志 是否为 formula
int gRecursionDepth = 0 ;   // 递归深度
int DEBUG = 0;	
/*project1-part1 end */
int i;
int j;

struct tableau {
  char *root;
  struct  tableau *left;
  struct tableau *right;
  struct tableau *parent;
}*tab1;

void newLeftchild(struct tableau *tab, char *formula){
    struct tableau *leftchild = (struct tableau*)malloc(sizeof(struct tableau));
    leftchild -> parent = tab;
    leftchild -> root = formula;
    leftchild -> left = NULL;
    leftchild -> right = NULL;
    tab -> left = leftchild;
}

void newRightchild(struct tableau *tab, char *formula){
    struct tableau *rightchild = (struct tableau*)malloc(sizeof(struct tableau));
    rightchild -> parent = tab;
    rightchild -> root = formula;
    rightchild -> left = NULL;
    rightchild -> right = NULL;
    tab -> right = rightchild;
}


char *disect(char *g, int i, int j){  //g is the sting, i is starting index where j is the end
  char *segment = (char *)malloc(sizeof(char) * (j-i+1)); 
    int a = 0;
    for (a = 0; a < (j-i); a++) {
        char n = g[i-1+a];
        segment[a]= n;
    }
    segment[j-i] = '\0'; //printf("~~~ dissection is %s ~~~\n", segment);
    return segment;
}



char binaryconnective(char *g){
    int bracket =0; 
    char bc;
    for(int index = 0;index<strlen(g); index++){ 
	if(g[index] == '('){
	  bracket += 1;}
	if(g[index] == ')'){
	  bracket -= 1;} 

	if(bracket == 1){   //Only take the location of bc in the middle
          if(g[index]=='v'||g[index]=='^'||g[index]=='>'){
            bc = g[index];
	  }} 
    }  // printf("%c", bc);   //end of for loop
    return bc;
}


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
    if(strchr("^>v", g[*i])){
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
    fmla(g,&i); // set gNotOk 
	// printf("strlen(g) = %d  ",strlen(g));
    //return(i == strlen(g) && gNotOk == 0);
	return gNotOk == 0;  //  True/False
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
    // parenCount = 1222221000
    // for(i=0;i<sizeOfWord;i++)
    // {
    //     printf("parenCount[i] = %d \n",parenCount[i]);
    // }
 
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

//-----------------------------TABLEU --------------------------------------------------------------

char *addnegative(char *a){  // Add negative sign to the expression
  char *newformula = (char*)malloc((strlen(a)+2)*sizeof(char));
  newformula[0] = '-';
  int index = 0;

  while(a[index] != '\0'){
    newformula[index+1] = a[index];
    index++;
      };
  newformula[index+1]= '\0';
  return newformula;
}

char *preexpression(char *a){
  if(parse(a)==3){
    switch(binaryconnective(a)){ //Binary 
    case 'v': return partone(a);   //Beta 
    case '^': return partone(a);    // Alpha
    case '>': return addnegative(partone(a));//Beta
    default : return NULL;
    }
  }

  if( (parse(a)==2) && (parse(a+1)==3)){   // Negated Binary
      switch(binaryconnective(a)){
      case 'v':return addnegative(partone(a)); //alpha
      case '^':return addnegative(partone(a)); //beta
      case '>':return partone(a);             //alpha
      default: return NULL;
      }  
    }

    return NULL;
}

char *postexpression(char *a){

  if(parse(a)==3){
    switch(binaryconnective(a)){ //Binary 
    case 'v': return parttwo(a);   //Beta 
    case '^': return parttwo(a);    // Alpha
    case '>': return parttwo(a);//Beta
    default : return NULL;
    }
  }

  if( (parse(a)==2) && (parse(a+1)==3)){   // Negated Binary
      switch(binaryconnective(a)){
      case 'v':return addnegative(parttwo(a)); //alpha
      case '^':return addnegative(parttwo(a)); //beta
      case '>':return addnegative(parttwo(a));             //alpha
      default: return NULL;
      }  
    }

    return NULL;
}

char *doublenegation(char *a){
  if((parse(a)==2) && (parse(a+1)==2))
    return (a+2);
  return NULL;
}

void add_alpha_child(struct tableau *t, char *a){ //
    //Base case
    if(t->left == NULL)
        newLeftchild(t,a);
    else
        add_alpha_child(t->left, a);
    
    if(t->right != NULL)
        add_alpha_child(t->right, a);
    
}

void alpha(struct tableau *t, char *exp1, char *exp2){
    add_alpha_child(t,exp1);
    add_alpha_child(t,exp2);
}

void beta(struct tableau *t, char *exp1, char *exp2){
    //base case
    if(t->left == NULL){
        newLeftchild(t, exp1);
        newRightchild(t, exp2);
    }else{
        beta(t->left, exp1, exp2);
        if(t->right != NULL){
            beta(t->right,exp1,exp2);
        }
    }
    
}
/* check alpha(1), beta(2) formula, literal(3) or double negation(4) */
int checkab(char *g){  //return for alpha and beta

  if( (parse(g)==1) || ((parse(g)==2 )&&(strlen(g)==2))  ){ //This is literal
   return 3;
  }
  if( (parse(g) ==2 ) && (parse(g+1)==2) ){  //  - & -phi -> double negation
    int countneg = 0;
    while(g[countneg] == '-'){
      countneg++;
    }
    if(countneg % 2 == 0)
      return 4;
    
    return 4;     
  }

  if(parse(g) ==3){  // Binary formula
    switch(binaryconnective(g)){
      case('v'): return 2;
      case('>'): return 2;
      case('^'): return 1;
      default: break;
    }
  } // end of 3rd if-loop

  if((parse(g)==2) && (parse(g+1)==3) ) { //Negated binary
      //printf("\t%c\t" ,binaryconnective(g));
      switch(binaryconnective(g)){
        case('v'): return 1;
        case('>'): return 1;
        case('^'): return 2;
        default: break;
      }
    
    }//End of for */
  
    return 0; 
}





void complete(struct tableau *t){
  if (t != NULL){
    char *root = t->root;
    printf("[complete] root = %s\n", root);
    switch(checkab(root)){
    case 0: 
        printf("[complete] case 0  :do nothing ,break \n");
        break; //dont know what is it
    case 1:
        printf("[complete] case 1  :call alpha() \n");
        alpha(t, preexpression(root),postexpression(root)); //printf("\n");
        break; //Alpha
    case 2:
        printf("[complete] case 2 : call beta() \n");
        beta(t, preexpression(root),postexpression(root)); //printf(" & ");
        break; //Beta
    case 3:
      printf("[complete] case 3 :do nothing ,break  \n");
        break; //literal
    case 4:
        printf("[complete] case 4 ,call  doublenegation() \n");
        t->root = doublenegation(root);
        complete(t);
        break;  //double negation
    }
    if(t->left != NULL){
        printf("[complete] t->left->root = %s\n", t->left->root );
        complete(t->left);
    }
    if(t->right!=NULL){
        printf("[complete] t->right->root = %s\n", t->right->root );
        complete(t->right);
    }
  }
  
}


//-----------------------expands ENDS   checking starts-------------


//Return 0 if statisfiable, 1 if not statisfiable
//so if the tableau is closed, it is satisfiable, otherwise it is not

int closedtab(struct tableau *t, int * statistic){
    char *root = t -> root;
    int new[6];
    
    for(int z =0; z<6; z++){
     new[z] = statistic[z];
    }
    
    if(parse(root) == 1) {
        switch(root[0]) {
            case 'p': new[0] = 1; break;
            case 'q': new[1] = 1; break;
            case 'r': new[2] = 1; break;
        }
    }
    if(parse(root) == 2 && parse(root+1) == 1) {
        switch(root[1]) {
            case 'p': new[3] = 1; break;
            case 'q': new[4] = 1; break;
            case 'r': new[5] = 1; break;
        }
    }
    
    for(int index = 0; index< 3; index++ ){
        if( (new[index]==1)  && (new[index+3]==1 )){
            return 1;
        }
    }
    
    if(t->left == NULL && t->right == NULL){
      return 0;
    }
    if(t->left != NULL && t->right == NULL){
        return closedtab(t->left, new);
    }
    
    if(t->left != NULL && t->right != NULL){
        int ringo = closedtab(t->left, new);
        int nameless = closedtab(t->right, new);
        return ringo && nameless;
    }
        
    return 0;
}
    
/*
  return :
     1  not   satisfiable  
     0    satisfiable  

*/ 
int closed(struct tableau *t){
    int statistic[6] ={0,0,0,0,0,0}; // counting appeareance of p q r -p -q -s
    char* root = t-> root;
    printf(" [closed] root =  %s \n",root );
    
    //Base case
    
    if(parse(root) == 0) //if root is nothing
    {
        printf(" [closed] case : parse(root) == 0 ,do nothing ,break\n" );
        return 1;
    }
        
    
    if(parse(root) == 1){ //a probositional formula
      printf(" [closed] case : parse(root) == 1 ,填充 statistic[0,1,2]  \n" );
      switch(root[0]){
        case 'p': statistic[0] = 1;
        case 'q': statistic[1] = 1;
        case 'r': statistic[2] = 1;
        default:printf("%c\n", root[0]);break;
      }
    }
    if( (parse(root) == 2) && (parse(root+1) == 1) ){ // a negated literle e.g. --p
        printf(" [closed] case : parse(root) == 2 or parse(root+1) == 1 ,填充 statistic[3,4,5]  \n" );
        switch(root[1]){
            case 'p': statistic[3] =1; break;
            case 'q': statistic[4] =1; break;
            case 'r': statistic[5] =1; break;
        }
    }
    
    if(t->left == NULL && t->right == NULL) // only one lit or a negation e.g -p , p , -q ,q
    {
        printf(" [closed] t->left == NULL && t->right == NULL ,return 0  \n" );
        return 0;  // end of base case
    }
       
    
    if(t->left != NULL && t->right == NULL){
        printf(" [closed] t->left != NULL && t->right == NULL ,call closedtab (left) \n" );
        return closedtab(t->left, statistic);
    }
        
    if(t->left != NULL && t->right != NULL){
        printf(" [closed] t->left != NULL && t->right != NULL ,call [closedtab (left),closedtab (right)] \n" );
        int ringo = closedtab(t->left, statistic);
        int nameless = closedtab(t->right, statistic);
        return ringo && nameless;
    }
    return 0;
}

//-----------------tableau ends
// some unit testing

void testreceiving(int *stat){
   // strlen(stat);
    for(int i = 0; i<6; i++)
      printf("element is %d" ,stat[i]);
    
}

int unittest(){
    struct tableau a={"-(p>q)"};   //test apply_alpha_rule  alpha ok
    struct tableau *c=&a;
    int statistic[6] = {1,2,3,4,5,6};
    
    closedtab(c, statistic);
    return 1 && 0;
}

void testcomplete(){
    /*    struct tableau a={"-(p>q)"};   //test apply_alpha_rule  alpha ok
     struct tableau *c=&a;
     alpha(c, "p", "-q");
     printf("%s\n",(c->left)->root);
     
     struct tableau b ={"-(p>q)"};      //test apply_beta_rule beta ok
     struct tableau *d=&b;
     beta(d, "-a", "b");
     printf("%s\n",(d->right)->root); */
    
    
    struct tableau a={"-((p>(qvr))>((p>q)v(p>r)))"};  //test complete()
    struct tableau *c=&a;
    complete(c);
    int n=0;              
    if ((c->right)==NULL) {
        n=1;
    }
    printf("%d\n",n);
    printf("%s\n",((((c->left)->left))->root));
    
    /*   struct tableau a={"-(p>p)"};  //return 0 if it is satsfiable
     struct tableau *c=&a;
     complete(c);
     int i=closed(c);
     printf("%d\n",i); */
}


//Main
int main()

{ /*input a string and check if its a propositional formula */

   //testcomplete();
    unittest();
  char *name = malloc(Fsize);
  FILE *fp, *fpout;
 
 
  if ((  fp=fopen("part2_input.txt","r"))==NULL){printf("Error opening file");exit(1);}
  if ((  fpout=fopen("output.txt","w"))==NULL){printf("Error opening file");exit(1);}

  int j;
  for(j=0;j<cases;j++)
  {
        
      fscanf(fp, "%s",name);/*read formula*/
        
      // printf("\n\n tableau \n\n");
      // printf("%s\npart1 %s and part2 %s\n\n",name, partone(name),parttwo(name));
      // printf("%s is %d formila\n", name, checkab(name));
      // printf("%s new form\n", addnegative(name));
      // printf("%s,%s\n", addnegative(partone(name)),parttwo(name));
      // printf("%s\n", doublenegation(name));
      // printf("%s adn %s\n", partone(name),parttwo(name));
      switch (parse(name))
        {
          case(0): 
              printf("%s is not a formula.  \n\n\n", name);
              fprintf(fpout, "%s is not a formula.  \n", name);
              break;
          case(1): 
            printf("%s is a proposition. \n\n\n", name);
            fprintf(fpout, "%s is a proposition. \n ", name);
            break;
          case(2): 
            printf("%s is a negation.  \n\n\n", name);
            fprintf(fpout, "%s is a negation.  \n", name);
            break;
          case(3):
            parenCounter(name);
                //int len = sizeof(parenCount) /sizeof(int);
                //printf("len = %d",len);
				    printf("%s is a binary formula. The first part is %s and the second part is %s  \n\n\n", name,partone(name),parttwo(name) );
				    fprintf(fpout, "%s is a binary formula. The first part is %s and the second part is %s  \n", name,partone(name),parttwo(name) );
				    break;
          default:
				    fprintf(fpout, "What the f***!  ");
       }
        
      //  struct tableau t={name, NULL, NULL, NULL};
      //  complete(&t);
        //int a = closed(&t);
      //  make new tableau with name at root, no children, no parent


      
      struct tableau t={name, NULL, NULL, NULL};

      //expand the root, recursively complete the children
      printf("[main] name = %s \n",name);
      if (parse(name)!=0){ 
        complete(&t);
        if (closed(&t)){
          fprintf(fpout, "%s is not satisfiable.\n", name);
          printf("%s is not satisfiable.\n", name);
        } 
        else{
          fprintf(fpout, "%s is satisfiable.\n", name);
          printf("%s is satisfiable.\n", name);
        } 
      }
      else{
          fprintf(fpout, "I told you, %s is not a formula.\n", name);
          printf("I told you, %s is not a formula.\n", name);
      }

  }
 
  fclose(fp);
  fclose(fpout);
  free(name);

  return(0);
}







