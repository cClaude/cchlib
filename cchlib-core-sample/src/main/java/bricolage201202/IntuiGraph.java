package bricolage201202;
/*     */ import java.applet.Applet;
/*     */ import java.applet.AppletContext;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Image;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.util.StringTokenizer;
/*     */
/*     */ public class IntuiGraph extends Applet
/*     */ {
/*     */   GraphPanel panel;
/*     */
/*     */   public void init()
/*     */   {
/*  16 */     setLayout(new BorderLayout());
/*  17 */     setFont(new Font(getFont().getFamily(), 1, getFont().getSize()));
/*  18 */     this.panel = new GraphPanel(this);
/*  19 */     add("Center", this.panel);
/*     */
/*  26 */     String edges = getParameter("edges");
/*  27 */     String center = getParameter("center");
/*  28 */     String position = getParameter("position");
/*  29 */     String levers = getParameter("levers");
/*  30 */     Edge.lev = Integer.parseInt(levers);
/*  31 */     for (StringTokenizer t = new StringTokenizer(edges, ","); t.hasMoreTokens(); ) {
/*  32 */       String str = t.nextToken();
/*  33 */       int i = str.indexOf('-');
/*  34 */       if (i > 0) {
/*  35 */         int len = 50;
/*  36 */         int j = str.indexOf('/');
/*  37 */         if (j > 0) {
/*  38 */           len = Integer.valueOf(str.substring(j + 1)).intValue();
/*  39 */           str = str.substring(0, j);
/*     */         }
/*  41 */         this.panel.addEdge(str.substring(0, i), str.substring(i + 1), len);
/*     */       }
/*     */     }
/*  44 */     Dimension d = getSize();
/*     */
/*  46 */     if (center != null) {
/*  47 */       String cPos = getParameter("cpos");
/*  48 */       Node n = this.panel.nodes[this.panel.findNode(center)];
/*  49 */       if (cPos != null) {
/*  50 */         int I = cPos.indexOf('-');
/*  51 */         n.px = (n.x = new Double(cPos.substring(0, I)).doubleValue());
/*  52 */         n.py = (n.y = new Double(cPos.substring(I + 1)).doubleValue());
/*  53 */         n.fixed = (n.pfixed = /*1*/true);
/*     */       } else {
/*  55 */         System.out.println("width " + d.width + "heigth " + d.height);
/*  56 */         n.px = (n.x = d.width / 2 - 19.5D);
/*  57 */         n.py = (n.y = d.height / 2 - 48.5D);
/*  58 */         n.fixed = /*(*/n.pfixed = /*1)*/true;
/*     */       }
/*     */     }
/*  61 */     for (StringTokenizer pp = new StringTokenizer(position, ","); pp.hasMoreTokens(); ) {
/*  62 */       String pos = pp.nextToken();
/*  63 */       int I = pos.indexOf('-');
/*  64 */       for (int i = 0; i < this.panel.nnodes; i++) {
/*  65 */         Node n = this.panel.nodes[i];
/*  66 */         if ((!n.lbl.equals("Intuitec")) && (!n.pfixed)) {
/*  67 */           n.px = new Double(pos.substring(0, I)).doubleValue();
/*  68 */           n.py = new Double(pos.substring(I + 1)).doubleValue();
/*  69 */           if (d.width / 2 > n.px)
/*  70 */             n.x = ((n.px - 20.0D) * Math.random());
/*     */           else
/*  72 */             n.x = ((n.px + 20.0D) * Math.random());
/*  73 */           if (d.height / 2 > n.py)
/*  74 */             n.y = ((n.py - 20.0D) * Math.random());
/*     */           else
/*  76 */             n.y = ((n.py + 20.0D) * Math.random());
/*  77 */           n.pfixed = true;
/*  78 */           break;
/*     */         }
/*     */       }
/*     */     }
/*  82 */     this.panel.random = true;
/*     */   }
/*     */
/*     */   public synchronized void start() {
/*  86 */     this.panel.start();
/*     */   }
/*     */
/*     */   public synchronized void stop() {
/*  90 */     this.panel.stop();
/*     */   }
/*     */
/*     */   public synchronized void jumpTo(String tName)
/*     */   {
/*     */     try
/*     */     {
/* 112 */       AppletContext context = getAppletContext();
/*     */
/* 114 */       URL intuitext = new URL(getDocumentBase(), "./" + tName + "/index.html");
/* 115 */       context.showDocument(intuitext, tName);
/*     */     } catch (Exception e) {
/* 117 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */
/*     */   public synchronized Image newImage(String iName) {
/* 122 */     Image image = null;
/* 123 */     System.out.println("loading " + iName);
/*     */     try {
/* 125 */       String uName = new String(iName + ".gif");
/* 126 */       URL iUrl = new URL(getDocumentBase(), "./images/" + uName);
/* 127 */       System.out.println("loading " + iUrl);
/* 128 */       image = getImage(iUrl);
/*     */     } catch (Exception e) {
/* 130 */       e.printStackTrace();
/*     */     }
/* 132 */     return image;
/*     */   }
/*     */ }

