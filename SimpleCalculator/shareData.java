import java.io.Serializable;

class shareData implements Serializable
{
    int number1;
    int number2;
    char operator;

    public shareData(int a, int b, char op)
    {
        this.number1 = a;
        this.number2 = b;
        this.operator = op;
    }

    public shareData()
    {

    }
}