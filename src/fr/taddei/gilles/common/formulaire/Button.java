package fr.taddei.gilles.common.formulaire;

public class Button extends Field
{
    private String onClick;

    public Button(String name, String value, String description, String onClick)
    {
        super(name, value, description);
        this.onClick = onClick;
    }

    public Button(String name, String value, String description)
    {
        super(name, value, description);
    }

    public String toString()
    {
        return "<input onclick=\"" + this.onClick
                + "\" type= \"submit\" name=\"" + this._name + "\" value=\""
                + this._value + "\"/>";
    }
}
