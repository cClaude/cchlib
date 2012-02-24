package bricolage201202;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;

public class Node extends Component
{
    private static final long serialVersionUID = 1L;

    final Color nodeColor1 = new Color(0, 186, 185);
    final Color nodeColor2 = new Color(249, 196, 106);
    //NOT USE: GraphPanel parent;

    private int _circel;
    private double _x;
    private double _y;
    private double _dx;
    private double _dy;
    private double _px;
    private double _py;
    private boolean _fixed;
    private boolean _pfixed;
    private boolean _jumped;
    private String _action;
    private String _lbl;
    private Image _image;

    public Node(
        //final GraphPanel    parentPanel,
        final int           circel,
        final String        label,
        //final IntuiGraph    graph
        final Image            image
        )
    {
        this._x = (10.0D + 380.0D * Math.random());
        this._y = (10.0D + 380.0D * Math.random());

        //this.parent = parentPanel;
        this._circel = circel;
        this._jumped = false;
        this._image = image;

        this._lbl = label;
    }


    public synchronized void paint(Graphics g, FontMetrics fm)
    {
        Node n = this;
        int x = (int) n._x;
        int y = (int) n._y;

        int w = fm.stringWidth(n._lbl) + 18;
        int h = fm.getHeight();

        setSize(w, h);
        n._circel += 10;

        if (n._circel > 360) {
            n._circel = 0;
            }

        if (n._jumped) {
            g.setColor(this.nodeColor2);
            g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, 0, 360);
            g.setColor(this.nodeColor1);
            g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, n._circel, 30);
            }
        else {
            g.setColor(this.nodeColor1);
            g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, 0, 360);
            g.setColor(this.nodeColor2);
            g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, n._circel, 30);
        }
        g.setColor(Color.blue);

        g.setColor(Color.black);

        g.setColor(Color.blue);
        g.drawString(n._lbl, x - (w - 11) / 2, y - h / 2 + fm.getAscent() + 4);
    }


    public String get_action() {
        return _action;
    }


    public void set_action(String _action) {
        this._action = _action;
    }


    public int get_circel() {
        return _circel;
    }


    public void set_circel(int _circel) {
        this._circel = _circel;
    }


    public double get_dx() {
        return _dx;
    }


    public void set_dx(double _dx) {
        this._dx = _dx;
    }


    public double get_dy() {
        return _dy;
    }


    public void set_dy(double _dy) {
        this._dy = _dy;
    }


    public double get_x() {
        return _x;
    }


    public double set_x(double _x) {
        this._x = _x;
        return _x;
    }


    public double get_y() {
        return _y;
    }


    public double set_y(double _y) {
        this._y = _y;
        return _y;
    }


	public boolean is_fixed() {
		return _fixed;
	}


	public void set_fixed(boolean _fixed) {
		this._fixed = _fixed;
	}


	public Image get_image() {
		return _image;
	}


	public void set_image(Image _image) {
		this._image = _image;
	}


	public boolean is_jumped() {
		return _jumped;
	}


	public void set_jumped(boolean _jumped) {
		this._jumped = _jumped;
	}


	public String get_lbl() {
		return _lbl;
	}


	public void set_lbl(String _lbl) {
		this._lbl = _lbl;
	}


	public boolean is_pfixed() {
		return _pfixed;
	}


	public boolean set_pfixed(boolean _pfixed) {
		this._pfixed = _pfixed;
		return _pfixed;
	}


	public double get_px() {
		return _px;
	}


	public void set_px(double _px) {
		this._px = _px;
	}


	public double get_py() {
		return _py;
	}


	public void set_py(double _py) {
		this._py = _py;
	}
}
