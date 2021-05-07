package coursework.paul.mynoughtscrosses;

import android.widget.Button;

public class Square {
    // Variables:
    public boolean isEmpty;
    public String value;
    Button location;

    // Constructor:
    public Square() {
        this.isEmpty = true;
        this.value = " ";
    }

    // Method to set the value:
    public void setValue(String player, int colour) {
        if (player.equals(" ")) {
            this.isEmpty = true;
        } else {
            this.isEmpty = false;
        }
        this.value = player;
        this.location.setTextColor(colour);
        this.location.setText(player);
    }

}
