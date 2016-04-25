package values;

public enum Color
{
    W ("white"),
    B ("black");

    private String _color;

    private Color(final String colorName)
    {
        _color = colorName;
    }

    public String getColorName()
    {
        return _color;
    }
}
