package 课设1;

public class CompareArray                        //数组类，元素可比较大小，实现排序和二分法查找算法
{
   
    public static <T extends java.lang.Comparable<? super T>> void sort(T[] value) 
    {   sort(value, true);                       //对象数组排序，默认升序
    }
    public static <T extends java.lang.Comparable<? super T>> void sort(T[] value, boolean asc)
    {
//        System.out.println("直接插入排序");
        for(int i=1; i<value.length; i++)        //n-1趟扫描
        {
            T x = value[i];                      //每趟将value[i]插入到前面排序子序列中
            int j=i-1;
            while(j>=0 && (asc ? x.compareTo(value[j])<0 : x.compareTo(value[j])>0))
                value[j+1] = value[j--];         //将前面较大/小元素向后移动
            value[j+1] = x;                      //x值到达插入位置
//            System.out.print("第"+i+"趟: ");
//            print(value);                      //调用print(Object)输出排序中间结果，可省略
        }
    }

    //对象数组排序（升序），由比较器对象c提供比较T对象大小的方法。冒泡排序算法，第5版没写
    public static <T> void sort(T[] value, java.util.Comparator<? super T> c)
    {
//        System.out.println("冒泡排序");
        boolean exchange=true;                             //是否交换的标记
        for(int i=1; i<value.length && exchange; i++)      //有交换时再进行下一趟，最多n-1趟
        {
            exchange=false;                                //假定元素未交换 
            for(int j=0; j<value.length-i; j++)            //一趟比较、交换
                if(c.compare(value[j], value[j+1])>0)      //反序时，交换
                {
                    T temp = value[j];
                    value[j] = value[j+1];
                    value[j+1] = temp;
                    exchange=true;                         //有交换 
                }
//            System.out.print("第"+i+"趟: ");
//            print(value);                                  //调用print(Object)输出排序中间结果，可省略
        }
    }

    //第5版没写
    //在value排序（升序）对象数组从begin到end范围，二分法查找关键字为key的元素，
    //若查找成功返回下标，否则返回-1；若begin、end省略，表示0～value.length-1；
    //泛型T必须实现Comparable<? super T>接口。
    //二分法查找算法只能查找一个元素，不能查找所有元素？？
    public static <T extends Comparable<? super T>> int binarySearch(T[] value, T key)
    {
        return binarySearch(value, 0, value.length-1, key);
    }  
    public static <T extends Comparable<? super T>> int binarySearch(T[] value, int begin, int end, T key)
    {
        if(key!=null)
        {
            while(begin<=end)                               //边界有效
            {   int mid = (begin+end)/2;                    //中间位置，当前比较元素位置
//                System.out.print(value[mid]+"? ");
                if(key.compareTo(value[mid])==0)            //比较对象大小，若相等，
                    return mid;                             //则查找成功；
                if(key.compareTo(value[mid])<0)             //若key对象小，
                    end = mid-1;                            //则查找范围缩小到前半段，
                else begin = mid+1;                         //否则查找范围缩小到后半段
            }
        }
        return -1;                                          //查找不成功
    }
}