#include <stdio.h> 
#include <string.h>   /* for all the new-fangled string functions */
#include <stdlib.h>     /* malloc, free, rand */

int Fsize=50; /* max string length of formulas*/
int inputs=10;


int i;
int j;


/*put all your functions here.  You will need
1.
int parse(char *g) which returns 1 if a proposition, 2 if neg, 3 if binary, ow 0
Of course you will almost certainly need other functions.

For binary formulas you will also need functions that return the first part and the second part of the binary formula.

char *partone(char *g)

char *parttwo(char *g)


You may vary this program provided it reads 10 formulas in a file called "input.txt" and outputs in the way indicated below to a file called "output.txt".
*/

int main()

{ /*input 10 strings from "input.txt" */


  char *name = malloc(Fsize);
  FILE *fp, *fpout;
 
  /* reads from input.txt, writes to output.txt*/
  if ((  fp=fopen("input.txt","r"))==NULL){printf("Error opening file");exit(1);}
  if ((  fpout=fopen("output.txt","w"))==NULL){printf("Error opening file");exit(1);}

  int j;
  for(j=0;j<inputs;j++)
    {
      fscanf(fp, "%s",name);/*read formula*/
      switch (parse(name))
      {
          case(0): fprintf(fpout, "%s is not a formula.  \n", name);break;
          case(1): fprintf(fpout, "%s is a proposition. \n ", name);break;
          case(2): fprintf(fpout, "%s is a negation.  \n", name);break;
          case(3):fprintf(fpout, "%s is a binary. The first part is %s and the second part is %s  \n", name, partone(name), parttwo(name));break;
          default:fprintf(fpout, "What the f***!  ");
    }

    }



  fclose(fp);
  fclose(fpout);
  free(name);

  return(0);
}
