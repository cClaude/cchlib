package bricolage201202;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */
/*    */ public class Node extends Component
/*    */ {
/*    */   int circel;
/*    */   double x;
/*    */   double y;
/*    */   double dx;
/*    */   double dy;
/*    */   double px;
/*    */   double py;
/*    */   boolean fixed;
/*    */   boolean pfixed;
/*    */   boolean jumped;
/*    */   String action;
/*    */   GraphPanel parent;
/*    */   String lbl;
/*    */   Image image;
/* 22 */   final Color nodeColor1 = new Color(0, 186, 185);
/* 23 */   final Color nodeColor2 = new Color(249, 196, 106);
/*    */
/*    */   public synchronized void paint(Graphics g, FontMetrics fm) {
/* 26 */     Node n = this;
/* 27 */     int x = (int)n.x;
/* 28 */     int y = (int)n.y;
/*    */
/* 30 */     int w = fm.stringWidth(n.lbl) + 18;
/* 31 */     int h = fm.getHeight();
/* 32 */     setSize(w, h);
/* 33 */     n.circel += 10;
/* 34 */     if (n.circel > 360) n.circel = 0;
/* 35 */     if (n.jumped) {
/* 36 */       g.setColor(this.nodeColor2);
/* 37 */       g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, 0, 360);
/* 38 */       g.setColor(this.nodeColor1);
/* 39 */       g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, n.circel, 30);
/*    */     } else {
/* 41 */       g.setColor(this.nodeColor1);
/* 42 */       g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, 0, 360);
/* 43 */       g.setColor(this.nodeColor2);
/* 44 */       g.fillArc(x - w / 2 - 4, y - h / 2 - 4, w + 4, h + 14, n.circel, 30);
/*    */     }
/* 46 */     g.setColor(Color.blue);
/*    */
/* 49 */     g.setColor(Color.black);
/*    */
/* 52 */     g.setColor(Color.blue);
/* 53 */     g.drawString(n.lbl, x - (w - 11) / 2, y - h / 2 + fm.getAscent() + 4);
/*    */   }
/*    */ }

