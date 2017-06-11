package JModular;

// personnalisation des sliders utilisés pour le réglage de la hauteur des notes dans le séquenceur
// ajout d'un puce personnalisée

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.plaf.metal.MetalSliderUI;

class IconThumbSliderUI extends MetalSliderUI {
    protected Icon vThumbIcon;
   
    public IconThumbSliderUI() {
        setVerticalThumbIcon(vThumbIcon);
    }

  
    public void setVerticalThumbIcon(Icon vThumbIcon) {
       if (vThumbIcon == null) this.vThumbIcon = vertThumbIcon;
       else
    	   this.vThumbIcon = vThumbIcon;
    }
  
    public void paintThumb(java.awt.Graphics g)  {
        java.awt.Rectangle knobBounds = thumbRect;  
        g.translate( knobBounds.x, knobBounds.y );      
        vThumbIcon.paintIcon( slider, g, 0, 0 );    
        g.translate( -knobBounds.x, -knobBounds.y );
    }
  
    
    protected Dimension getThumbSize() {
        Dimension size = new Dimension();
        size.width = vThumbIcon.getIconWidth();
        size.height = vThumbIcon.getIconHeight();
    return size;
    }
    
    public void setImageIcon(String ImagePath){
    	vThumbIcon = new ImageIcon(ImagePath);
    	setVerticalThumbIcon(vThumbIcon);
    }
    
}