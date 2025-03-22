import java.util.List;

public class Tekton {
    private List<ShroomString> arrayOfString;
    private List<Mushroom> arrayOfMushroom;
    private List<Insect> arrayOfInsect;
    private List<Spore> arrayOfSpore;

    public Tekton breakTekton() { return null; }
    public void accept() {}
    public void growBody() {}
    public void addString() {}
    public void addSpore() {}
    public void moveInsect(Insect insect, Tekton tekton) {}
    public void removeInsect(Insect insect) {}
    public void removeString(ShroomString string) {}
    public void removeBody(Mushroom mushroom) {}
    public void removeSpore(Spore spore) {}
    public void addUpgradedBody() {}
}
