package ����1;

public class CompareArray                        //�����࣬Ԫ�ؿɱȽϴ�С��ʵ������Ͷ��ַ������㷨
{
   
    public static <T extends java.lang.Comparable<? super T>> void sort(T[] value) 
    {   sort(value, true);                       //������������Ĭ������
    }
    public static <T extends java.lang.Comparable<? super T>> void sort(T[] value, boolean asc)
    {
//        System.out.println("ֱ�Ӳ�������");
        for(int i=1; i<value.length; i++)        //n-1��ɨ��
        {
            T x = value[i];                      //ÿ�˽�value[i]���뵽ǰ��������������
            int j=i-1;
            while(j>=0 && (asc ? x.compareTo(value[j])<0 : x.compareTo(value[j])>0))
                value[j+1] = value[j--];         //��ǰ��ϴ�/СԪ������ƶ�
            value[j+1] = x;                      //xֵ�������λ��
//            System.out.print("��"+i+"��: ");
//            print(value);                      //����print(Object)��������м�������ʡ��
        }
    }

    //���������������򣩣��ɱȽ�������c�ṩ�Ƚ�T�����С�ķ�����ð�������㷨����5��ûд
    public static <T> void sort(T[] value, java.util.Comparator<? super T> c)
    {
//        System.out.println("ð������");
        boolean exchange=true;                             //�Ƿ񽻻��ı��
        for(int i=1; i<value.length && exchange; i++)      //�н���ʱ�ٽ�����һ�ˣ����n-1��
        {
            exchange=false;                                //�ٶ�Ԫ��δ���� 
            for(int j=0; j<value.length-i; j++)            //һ�˱Ƚϡ�����
                if(c.compare(value[j], value[j+1])>0)      //����ʱ������
                {
                    T temp = value[j];
                    value[j] = value[j+1];
                    value[j+1] = temp;
                    exchange=true;                         //�н��� 
                }
//            System.out.print("��"+i+"��: ");
//            print(value);                                  //����print(Object)��������м�������ʡ��
        }
    }

    //��5��ûд
    //��value�������򣩶��������begin��end��Χ�����ַ����ҹؼ���Ϊkey��Ԫ�أ�
    //�����ҳɹ������±꣬���򷵻�-1����begin��endʡ�ԣ���ʾ0��value.length-1��
    //����T����ʵ��Comparable<? super T>�ӿڡ�
    //���ַ������㷨ֻ�ܲ���һ��Ԫ�أ����ܲ�������Ԫ�أ���
    public static <T extends Comparable<? super T>> int binarySearch(T[] value, T key)
    {
        return binarySearch(value, 0, value.length-1, key);
    }  
    public static <T extends Comparable<? super T>> int binarySearch(T[] value, int begin, int end, T key)
    {
        if(key!=null)
        {
            while(begin<=end)                               //�߽���Ч
            {   int mid = (begin+end)/2;                    //�м�λ�ã���ǰ�Ƚ�Ԫ��λ��
//                System.out.print(value[mid]+"? ");
                if(key.compareTo(value[mid])==0)            //�Ƚ϶����С������ȣ�
                    return mid;                             //����ҳɹ���
                if(key.compareTo(value[mid])<0)             //��key����С��
                    end = mid-1;                            //����ҷ�Χ��С��ǰ��Σ�
                else begin = mid+1;                         //������ҷ�Χ��С������
            }
        }
        return -1;                                          //���Ҳ��ɹ�
    }
}