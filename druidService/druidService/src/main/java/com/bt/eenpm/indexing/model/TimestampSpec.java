package com.bt.eenpm.indexing.model;

public class TimestampSpec {

	private String column;

    private String format;

    public String getColumn ()
    {
        return column;
    }

    public void setColumn (String column)
    {
        this.column = column;
    }

    public String getFormat ()
    {
        return format;
    }

    public void setFormat (String format)
    {
        this.format = format;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [column = "+column+", format = "+format+"]";
    }

}
