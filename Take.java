public class Take{
    private int number;
    private int x, y, h, w;

    public Take(int number, int x, int y, int h, int w){
        this.number = number;
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }

    public int getNumber(){
        return this.number;
    }

    public int getXCord(){
        return this.x;
    }

    public int getYCord(){
        return this.y;
    }

    public int getHeight(){
        return this.h;
    }

    public int getWidth(){
        return this.w;
    }
}