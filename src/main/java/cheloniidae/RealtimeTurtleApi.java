package cheloniidae;

public interface RealtimeTurtleApi {
    void size(double size);

    void color(int r, int g, int b);

    void forward(double distance);

    void back(double distance);

    void left(double degree);

    void right(double degree);

    void penUp();

    void penDown();
}


