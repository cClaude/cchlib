package bricolage201202;
/*     */ import java.awt.Color;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Image;
/*     */ import java.awt.Panel;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */
/*     */ public class GraphPanel extends Panel
/*     */   implements Runnable, MouseListener, MouseMotionListener
/*     */ {
/*     */   IntuiGraph graph;
/*     */   int nnodes;
/*  15 */   Node[] nodes = new Node[10];
/*     */   int nedges;
/*  17 */   Edge[] edges = new Edge[20];
/*     */   Thread relaxer;
/*     */   boolean stress;
/*     */   boolean random;
/*     */   Node pick;
/*     */   boolean pickfixed;
/*     */   Image offscreen;
/*     */   Dimension offscreensize;
/*     */   Graphics offgraphics;
/* 198 */   final Color fixedColor = Color.blue;
/* 199 */   final Color selectColor = Color.pink;
/* 200 */   final Color edgeColor = Color.black;
/* 201 */   final Color nodeColor1 = new Color(0, 186, 185);
/* 202 */   final Color nodeColor2 = new Color(249, 196, 106);
/* 203 */   final Color stressColor = Color.darkGray;
/* 204 */   final Color arcColor1 = Color.black;
/* 205 */   final Color arcColor2 = Color.pink;
/* 206 */   final Color arcColor3 = Color.blue;
/*     */
/*     */   GraphPanel(IntuiGraph graph)
/*     */   {
/*  23 */     this.graph = graph;
/*  24 */     addMouseListener(this);
/*     */   }
/*     */
/*     */   int findNode(String lbl) {
/*  28 */     for (int i = 0; i < this.nnodes; i++) {
/*  29 */       if (this.nodes[i].lbl.equals(lbl)) {
/*  30 */         return i;
/*     */       }
/*     */     }
/*  33 */     return addNode(lbl);
/*     */   }
/*     */
/*     */   public Node getNode(String name) {
/*  37 */     Node node = null;
/*  38 */     for (int i = 0; i < this.nnodes; i++) {
/*  39 */       if (this.nodes[i].lbl.equals(name)) {
/*  40 */         node = this.nodes[i];
/*  41 */         break;
/*     */       }
/*     */     }
/*  44 */     return node;
/*     */   }
/*     */
/*     */   int addNode(String lbl) {
/*     */     try {
/*  49 */       Node n = new Node();
/*  50 */       n.x = (10.0D + 380.0D * Math.random());
/*  51 */       n.y = (10.0D + 380.0D * Math.random());
/*  52 */       n.parent = this;
/*  53 */       n.circel = (this.nnodes * 45);
/*  54 */       n.jumped = false;
/*  55 */       if (lbl.equals("Intuitec")) {
/*  56 */         n.image = this.graph.newImage(lbl);
/*     */       }
/*  58 */       n.lbl = lbl;
/*  59 */       this.nodes[this.nnodes] = n;
/*     */     } catch (Exception e) {
/*  61 */       e.printStackTrace();
/*     */     }
/*  63 */     return this.nnodes++;
/*     */   }
/*     */
/*     */   void addEdge(String from, String to, int len) {
/*  67 */     Edge e = new Edge();
/*  68 */     e.from = findNode(from);
/*  69 */     e.to = findNode(to);
/*  70 */     e.len = len;
/*  71 */     this.edges[(this.nedges++)] = e;
/*     */   }
/*     */
/*     */   public void run() {
/*     */     while (true) {
/*  76 */       relax();
/*  77 */       if ((this.random) && (Math.random() < 0.03D)) {
/*  78 */         Node n = this.nodes[(int)(Math.random() * this.nnodes)];
/*  79 */         if (!n.fixed) {
/*  80 */           n.x += n.px * Math.random() - 50.0D;
/*  81 */           n.y += n.py * Math.random() - 50.0D;
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/*  86 */         Thread.sleep(100L);
/*     */       } catch (InterruptedException e) {
/*  88 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */
/*     */   synchronized void relax() {
/*  94 */     edgesRelax();
/*  95 */     nodesRelax1();
/*  96 */     nodesRelax2(getSize());
/*  97 */     repaint();
/*     */   }
/*     */
/*     */   private void nodesRelax2(Dimension d)
/*     */   {
/* 104 */     for (int i = 0; i < this.nnodes; i++) {
/* 105 */       Node n = this.nodes[i];
/* 106 */       if (!n.fixed)
/*     */       {
/* 108 */         if (n.px > n.x)
/* 109 */           n.x += Math.abs((n.px - n.x) / 12.0D * Math.random());
/*     */         else
/* 111 */           n.x -= Math.abs((n.px - n.x) / 12.0D * Math.random());
/* 112 */         if (n.py > n.y)
/* 113 */           n.y += Math.abs((n.py - n.y) / 12.0D * Math.random());
/*     */         else
/* 115 */           n.y -= Math.abs((n.py - n.y) / 12.0D * Math.random());
/* 116 */         if (n.x < 0.0D)
/* 117 */           n.x = 1.0D;
/* 118 */         else if (n.x > d.width) {
/* 119 */           n.x = (d.width - 10);
/*     */         }
/* 121 */         if (n.y < 0.0D)
/* 122 */           n.y = 1.0D;
/* 123 */         else if (n.y > d.height) {
/* 124 */           n.y = (d.height - 10);
/*     */         }
/*     */
/*     */       }
/*     */
/* 134 */       n.dx /= 2.0D;
/* 135 */       n.dy /= 2.0D;
/*     */     }
/*     */   }
/*     */
/*     */   private void nodesRelax1()
/*     */   {
/* 145 */     for (int i = 0; i < this.nnodes; i++) {
/* 146 */       Node n1 = this.nodes[i];
/* 147 */       double dx = 0.0D;
/* 148 */       double dy = 0.0D;
/* 149 */       for (int j = 0; j < this.nnodes; j++) {
/* 150 */         if (i == j) {
/*     */           continue;
/*     */         }
/* 153 */         Node n2 = this.nodes[j];
/* 154 */         double vx = n1.x - n2.x;
/* 155 */         double vy = n1.y - n2.y;
/* 156 */         double len = vx * vx + vy * vy;
/* 157 */         if (len == 0.0D) {
/* 158 */           dx += Math.random();
/* 159 */           dy += Math.random();
/* 160 */         } else if (len < 10000.0D) {
/* 161 */           dx += vx / len;
/* 162 */           dy += vy / len;
/*     */         }
/*     */       }
/* 165 */       double dlen = dx * dx + dy * dy;
/* 166 */       if (dlen > 0.0D) {
/* 167 */         dlen = Math.sqrt(dlen) / 2.0D;
/* 168 */         n1.dx += dx / dlen;
/* 169 */         n1.dy += dy / dlen;
/*     */       }
/*     */     }
/*     */   }
/*     */
/*     */   private void edgesRelax()
/*     */   {
/* 178 */     for (int i = 0; i < this.nedges; i++) {
/* 179 */       Edge e = this.edges[i];
/* 180 */       double vx = this.nodes[e.to].x - this.nodes[e.from].x;
/* 181 */       double vy = this.nodes[e.to].y - this.nodes[e.from].y;
/* 182 */       double len = Math.sqrt(vx * vx + vy * vy);
/* 183 */       double f = (this.edges[i].len - len) / (len * 3.0D);
/* 184 */       double dx = f * vx;
/* 185 */       double dy = f * vy;
/* 186 */       this.nodes[e.to].dx += dx;
/* 187 */       this.nodes[e.to].dy += dy;
/* 188 */       this.nodes[e.from].dx += -dx;
/* 189 */       this.nodes[e.from].dy += -dy;
/*     */     }
/*     */   }
/*     */
/*     */   public void paint(Graphics g)
/*     */   {
/* 210 */     update(g);
/*     */   }
/*     */
/*     */   public synchronized void update(Graphics g) {
/* 214 */     Dimension d = getSize();
/* 215 */     if ((this.offscreen == null) || (d.width != this.offscreensize.width) || (d.height != this.offscreensize.height)) {
/* 216 */       this.offscreen = createImage(d.width, d.height);
/* 217 */       this.offscreensize = d;
/* 218 */       this.offgraphics = this.offscreen.getGraphics();
/* 219 */       this.offgraphics.setFont(getFont());
/*     */     }
/*     */
/* 222 */     this.offgraphics.setColor(Color.white);
/* 223 */     this.offgraphics.fillRect(0, 0, d.width, d.height);
/* 224 */     edgeLoop(Edge.lev);
/* 225 */     nodeLoop();
/* 226 */     g.drawImage(this.offscreen, 0, 0, null);
/*     */   }
/*     */
/*     */   private void nodeLoop()
/*     */   {
/* 233 */     FontMetrics fm = this.offgraphics.getFontMetrics();
/* 234 */     for (int i = 0; i < this.nnodes; i++)
/* 235 */       if (this.nodes[i] == this.nodes[findNode("Intuitec")])
/* 236 */         this.offgraphics.drawImage(this.nodes[i].image, (int)this.nodes[i].x, (int)this.nodes[i].y, this);
/*     */       else
/* 238 */         this.nodes[i].paint(this.offgraphics, fm);
/*     */   }
/*     */
/*     */   private void edgeLoop(int l)
/*     */   {
/* 246 */     for (int i = 0; i < this.nedges; i++)
/*     */     {
/* 249 */       int[] xx1 = new int[l];
/* 250 */       int[] xx2 = new int[l];
/* 251 */       int[] yy1 = new int[l];
/* 252 */       int[] yy2 = new int[l];
/*     */
/* 254 */       Edge e = this.edges[i];
/* 255 */       Node m = this.nodes[findNode("Intuitec")];
/*     */       int y2;
/*     */       int x1;
/*     */       int y1;
/*     */       int x2;
/*     */       /*int y2;*/
/* 257 */       if (this.nodes[e.from].lbl.equals("Intuitec")) {
/* 258 */         /*int*/ x1 = (int)this.nodes[e.from].x + 19;
/* 259 */         /*int*/ y1 = (int)this.nodes[e.from].y + 48;
/* 260 */         /*int*/ x2 = (int)this.nodes[e.to].x;
/* 261 */         y2 = (int)this.nodes[e.to].y;
/*     */       } else {
/* 263 */         x1 = (int)this.nodes[e.from].x;
/* 264 */         y1 = (int)this.nodes[e.from].y;
/* 265 */         x2 = (int)this.nodes[e.to].x;
/* 266 */         y2 = (int)this.nodes[e.to].y;
/*     */       }
/* 268 */       int x0 = (int)m.x + 19;
/*     */
/* 270 */       int y0 = (int)m.y + 48;
/* 271 */       for (int j = 0; j < l; j++) {
/* 272 */         xx1[j] = (((l - j) * x1 + j * x0) / l);
/* 273 */         xx2[j] = (((l - j) * x2 + j * x0) / l);
/*     */
/* 275 */         yy1[j] = (((l - j) * y1 + j * y0) / l);
/*     */
/* 277 */         yy2[j] = (((l - j) * y2 + j * y0) / l);
/*     */       }
/*     */
/* 280 */       int len = (int)Math.abs(Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) - e.len);
/* 281 */       this.offgraphics.setColor(len < 20 ? this.arcColor2 : len < 10 ? this.arcColor1 : this.arcColor3);
/* 282 */       this.offgraphics.drawLine(x1, y1, x2, y2);
/* 283 */       if (!this.nodes[e.from].lbl.equals("Intuitec")) {
/* 284 */         for (int j = 0; j < l; j++)
/*     */         {
/* 286 */           this.offgraphics.drawLine(xx1[j], yy1[j], xx2[j], yy2[j]);
/*     */         }
/*     */       }
/* 289 */       if (this.stress) {
/* 290 */         String lbl = String.valueOf(len);
/* 291 */         this.offgraphics.setColor(this.stressColor);
/* 292 */         this.offgraphics.drawString(lbl, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2);
/* 293 */         this.offgraphics.setColor(this.edgeColor);
/*     */       }
/*     */     }
/*     */   }
/*     */
/*     */   public void setHandCursor() {
/* 299 */     setCursor(
/* 300 */       Cursor.getPredefinedCursor(12));
/*     */   }
/*     */
/*     */   public void setDefaultCursor() {
/* 304 */     setCursor(
/* 305 */       Cursor.getPredefinedCursor(0));
/*     */   }
/*     */
/*     */   public synchronized void mousePressed(MouseEvent e) {
/* 309 */     int x = e.getX();
/* 310 */     int y = e.getY();
/*     */
/* 312 */     double bestdist = 1.7976931348623157E+308D;
/* 313 */     for (int i = 0; i < this.nnodes; i++) {
/* 314 */       Node n = this.nodes[i];
/* 315 */       n.jumped = false;
/* 316 */       double dist = (n.x - x) * (n.x - x) + (n.y - y) * (n.y - y);
/* 317 */       if (dist < bestdist) {
/* 318 */         this.pick = n;
/* 319 */         bestdist = dist;
/*     */       }
/*     */     }
/* 322 */     if (this.pick.lbl.equals("Intuitec"))
/* 323 */       return;
/* 324 */     this.pickfixed = this.pick.fixed;
/* 325 */     this.pick.fixed = true;
/* 326 */     this.pick.x = x;
/* 327 */     this.pick.y = y;
/* 328 */     repaint();
/* 329 */     addMouseMotionListener(this);
/*     */   }
/*     */
/*     */   public synchronized void mouseReleased(MouseEvent e)
/*     */   {
/* 334 */     this.pick.x = e.getX();
/* 335 */     this.pick.y = e.getY();
/* 336 */     this.pick.fixed = this.pickfixed;
/*     */
/* 338 */     repaint();
/* 339 */     removeMouseMotionListener(this);
/*     */   }
/*     */
/*     */   public synchronized void mouseExited(MouseEvent e)
/*     */   {
/* 344 */     setDefaultCursor();
/*     */   }
/*     */
/*     */   public synchronized void mouseEntered(MouseEvent e)
/*     */   {
/* 349 */     setHandCursor();
/*     */   }
/*     */
/*     */   public synchronized void mouseClicked(MouseEvent e)
/*     */   {
/* 354 */     if (!this.pick.lbl.equals("Intuitec"))
/* 355 */       this.graph.jumpTo(this.pick.lbl);
/* 356 */     this.pick.jumped = true;
/* 357 */     this.pick = null;
/*     */   }
/*     */
/*     */   public synchronized void mouseMoved(MouseEvent e)
/*     */   {
/*     */   }
/*     */
/*     */   public synchronized void mouseDragged(MouseEvent e)
/*     */   {
/* 366 */     this.pick.x = e.getX();
/* 367 */     this.pick.y = e.getY();
/* 368 */     repaint();
/*     */   }
/*     */
/*     */   public synchronized void start() {
/* 372 */     this.relaxer = new Thread(this);
/* 373 */     this.relaxer.start();
/*     */   }
/*     */
/*     */   public synchronized void stop() {
/* 377 */     this.relaxer = null;
/*     */   }
/*     */ }
