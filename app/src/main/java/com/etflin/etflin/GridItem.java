package com.etflin.etflin;

public class GridItem {
    private String gridJudul, gridImage, gridKet;

    public GridItem(String gridImage, String gridJudul, String gridKet){
        this.gridImage = gridImage;
        this.gridJudul = gridJudul;
        this.gridKet = gridKet;
    }

    public String getGridImage() { return gridImage; }

    public void setGridImage(String gridImage) { this.gridImage = gridImage; }

    public String getGridKet() { return gridKet; }

    public void setGridKet(String gridKet) { this.gridKet = gridKet; }

    public String getGridJudul() { return gridJudul; }

    public void setGridJudul(String gridJudul) { this.gridJudul = gridJudul; }

}
