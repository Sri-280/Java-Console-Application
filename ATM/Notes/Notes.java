package Notes;

public class Notes implements Cloneable{
    private String notes;
    private long notecount;

    protected Notes(String notes,long notecount){
        this.notes=notes;
        this.notecount=notecount;
    }

    public String getNotes(){return notes;}
    public long getNotecount(){return notecount;}
    public void setNotes(String notes){this.notes=notes;}
    public  void setNotecount(long notecount){this.notecount=notecount;}

    @Override
    public Notes clone() throws CloneNotSupportedException {
        return (Notes) super.clone();
    }
}
