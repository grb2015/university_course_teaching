/*
uage: gcc productor2.c -o productor2 -lpthread 
note: you should link pthread !
sem_wait(s){
   while(s<=0){
     }
   s--
}


while(s<=0)
;
s--
*/
int a[15]
a[3] = 7
a[7] = 2
a[14] = 3 // begin


int *p = NULL; // p = 0x37832663 = 123764664  4bytes
char a = 'u' // 1byte =8bit  2^8 = 256 (0-255 )
sizeof(p) = 4 
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#define N 9  // 消费者或者生产者的数目
#define M 10 // 缓冲数目

int in = 0;   // 生产者放置产品的位置
int out = 0; // 消费者取产品的位置

int buff[M] = {0}; // 缓冲初始化为0， 开始时没有产品

sem_t empty_sem; // 同步信号量， 当满了时阻止生产者放产品
sem_t full_sem;   // 同步信号量， 当没产品时阻止消费者消费
// pthread_mutex_t mutex; // 互斥信号量， 一次只有一个线程访问缓冲
sem_t mutex;
int product_id = 0;   //生产者id
int consumer_id = 0; //消费者id

int counter =0 ;
/* 打印缓冲情况 */
void print()
{
   int i;
   for(i = 0; i < M; i++)
      printf("%d ", buff[i]);
   printf("\n");
}

/* 生产者方法 */ 
void *product()
{
   // int id = ++product_id;

   while(1)
   {
      // sleep(0.3);
      if(counter <= M){
         printf("[product] in = %s\n",in);
         in = in % M;
         buff[in] = 1;  
         print();  
         ++in;
         sem_wait(&mutex);  // block 
         counter++;
         sem_post(&mutex); 
      }

   }
}

/* 消费者方法 */
void *consumer()
{
   while(1){
      // sleep(2);
      if (counter > 0){
         // printf("[product] out = %s\n",out);
         out = out % M;
         buff[out] = 0;
         print();
         ++out;
         sem_wait(&mutex);
         counter--;
         sem_post(&mutex); 
      }
   }
}

int main()
{
   int i;
   int ret[N];
   pthread_t id1[N];
   pthread_t id2[N];
   // 创建N个生产者线程
   for(i = 0; i < 1; i++)
   {//  pthread_create: create a thread
      ret[i] = pthread_create(&id1[i], NULL, product, (void *)(&i));
      if(ret[i] != 0)
      {// 异常处理
          printf("product%d creation failed \n", i);
          exit(1);
      }
   }
   //创建1个消费者线程
   for(i = 0; i < 1; i++)
   {
      ret[i] = pthread_create(&id2[i], NULL, consumer, NULL);
      if(ret[i] != 0)
      {
          printf("consumer%d creation failed \n", i);
          exit(1);
      }
   }
   //销毁线程
   for(i = 0; i < 1; i++)
   {
      pthread_join(id1[i],NULL);
      pthread_join(id2[i],NULL);
   }

   exit(0); 
}
