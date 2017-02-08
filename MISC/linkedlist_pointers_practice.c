#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define LINE_LENGTH 100

struct clip *build_a_lst();
struct clip *append();
int find_length();
void print_a_lst(struct clip *);
void split_line();

struct clip {
  int views;
  char *user;
  char *id;
  char *title;
  struct clip *next;
} *head;

int main(int argc, char **argv) {
  int n;
  head = build_a_lst(*(argv+1));
  n = find_length(head);
  printf("%d clips\n",n);
  print_a_lst(head);		/* prints the table */
  return 0;
}

struct clip *build_a_lst(char *fn) {
  FILE *fp;
  struct clip *hp;
  char *fields[4];
  char line[LINE_LENGTH];
  int cnt=0;
  hp=NULL; char c;
  fp=fopen(fn, "r");
  while((c=getc(fp))!=EOF){
      if(c=='\n'){
        cnt=0;
        split_line(fields, line);
        for(int i=0; i < 5; i++){
          printf("field %d : %s", i, fields[i]);

        }
       // hp = append(hp, fields);
       //printf("F0: %s ", fields[0]);  printf("F1 : %s ", fields[1]); printf("F2 : %s ", fields[2]); printf("f4 : %s \n", fields[4]);

      }
      line[cnt++]=c;
  }
  struct clip *cp;
  
  // open fn
  // while no more lines
  // read a line
  // split the line into four substrings/int and store them in a struct
  // append - add the struct at the end of the list
  // return the head pointer holding the list
  
  return hp;
}

/* fields will have four values stored upon return */
void split_line(char **fields,char *line) {
  int i=0;
  char *token, *delim;
  delim=",";
  token=strtok(line, delim);
  fields[i]=(char *)malloc(sizeof(char) * strlen(token));
  strcpy(fields[i++], token);
  printf("field 0 %s\n", fields[0]);
  while((token=strtok(NULL, delim))!=NULL){
    fields[i]=(char *)malloc(sizeof(char)*strlen(token));
    strcpy(fields[i++], token);
  }
  /*delim = ",\n";
  token=strtok(line, delim);
  char * cp;
  cp=strdup(token);
  fields[i++]=cp;

  while((token=strtok(NULL, delim))!=NULL){
    fields[i++]=token;
  }*/
  /* 
     call strtok(line, delim);
     repeat until strtok returns NULL using strtok(NULL, delim);
 */
}

/* set four values into a clip, insert a clip at the of the list */
struct clip *append(struct clip *hp,char **five) {
  struct clip *cp,*tp;
  tp=(struct clip *)malloc(sizeof(struct clip));
  tp->views=atoi(five[2]);
  tp->user=(char *)malloc(sizeof(char *));
  tp->title=(char *)malloc(sizeof(char *));
  tp->id=(char *)malloc(sizeof(char *));
  tp->title=strdup(five[0]);
  tp->user=strdup(five[1]);
  tp->id=strdup(five[4]);

  cp=hp;
  if(cp==NULL){
    hp=tp;
  }
  else{
    while(cp->next!=NULL){
      cp=cp->next;
    }
    cp->next=tp;


  }

  /* 
     malloc tp
     set views using atoi(*five)
     malloc for four strings.
     strcpy four strings to tp
     insert tp at the end of the list pointed by hp
     use cp to traverse the list
 */
 

  return hp;
}

void print_a_lst(struct clip *cp) {
 printf("%s\n",   "");
  if(cp==NULL)
    printf("Error: empty list");
  else{
    while(cp){
       printf("%s ", cp->title);
        printf("%s ", cp->user);
       printf("%d ", cp->views);
        printf("%s\n ", cp->id);
     //printf("%d,%s,%s,%s,%s\n",cp->views,cp->user,cp->id,cp->title);
      cp=cp->next;

    }
  }
  /* 
     use a while loop and the statement below to print the list
     printf("%d,%s,%s,%s,%s\n",cp->views,cp->user,cp->id,cp->title,cp->time);
  */
     printf("%s\n", " ");
}
int find_length(struct clip *hp){
  int i; struct clip *cp;
  cp=hp;
  if(hp==NULL)
  return 0;
  else{
    while(cp){
      cp=cp->next;
      i++;

    }
  }
  return i;
}

/* end */