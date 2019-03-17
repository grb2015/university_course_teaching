/*
    rbguo created 20190317 
    struct of queue in c language
    refer : http://www.thelearningpoint.net/computer-science/data-structures-queues--with-c-program-source-code
 */
#include<stdio.h>
#include<stdlib.h>
/*Queue has five properties. max_size stands for the maximum number of elements Queue can hold.
  cur_size stands for the current cur_size of the Queue and elements is the array of elements. front is the
 index of first element (the index at which we remove the element) and rear is the index of last element
 (the index at which we insert the element) */
typedef struct Queue
{
        int max_size;
        int cur_size;
        int front;
        int rear;
        int *elements;
}Queue;
/* crateQueue function takes argument the maximum number of elements the Queue can hold, creates
   a Queue according to it and returns a pointer to the Queue. */
Queue * createQueue(int maxElements)
{
        /* Create a Queue */
        Queue *Q;
        Q = (Queue *)malloc(sizeof(Queue));
        /* Initialise its properties */
        Q->elements = (int *)malloc(sizeof(int)*maxElements);
        Q->cur_size = 0;
        Q->max_size = maxElements;
        Q->front = 0;
        Q->rear = -1;
        /* Return the pointer */
        return Q;
}
void Dequeue(Queue *Q)
{
        /* If Queue cur_size is zero then it is empty. So we cannot pop */
        if(Q->cur_size==0)
        {
                printf("Queue is Empty\n");
                return;
        }
        /* Removing an element is equivalent to incrementing index of front by one */
        else
        {
                Q->cur_size--;
                Q->front++;
                /* As we fill elements in circular fashion */
                if(Q->front==Q->max_size)
                {
                        Q->front=0;
                }
        }
        return;
}
int front(Queue *Q)
{
        if(Q->cur_size==0)
        {
                printf("Queue is Empty\n");
                exit(0);
        }
        /* Return the element which is at the front*/
        return Q->elements[Q->front];
}
void Enqueue(Queue *Q,int element)
{
        /* If the Queue is full, we cannot push an element into it as there is no space for it.*/
        if(Q->cur_size == Q->max_size)
        {
                printf("Queue is Full\n");
        }
        else
        {
                Q->cur_size++;
                Q->rear = Q->rear + 1;
                /* As we fill the queue in circular fashion */
                if(Q->rear == Q->max_size)
                {
                        Q->rear = 0;
                }
                /* Insert the element in its rear side */ 
                Q->elements[Q->rear] = element;
        }
        return;
}
int main()
{
        Queue *Q = createQueue(5);
        Enqueue(Q,1);
        Enqueue(Q,2);
        Enqueue(Q,3);
        Enqueue(Q,4);
        printf("Front element is %d\n",front(Q));
        Enqueue(Q,5);
        Dequeue(Q);
        Enqueue(Q,6);
        printf("Front element is %d\n",front(Q));
}