package org.mac.canvasgraph.svg;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.mac.canvasgraph.ds.DsNode;
import org.mac.canvasgraph.ds.DsOutputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DsSvgOutputHandler implements DsOutputHandler {

	private static final Logger logger = LoggerFactory.getLogger(DsSvgOutputHandler.class);
	private final Document doc;
	protected DsLayoutHandler<Double> dsLayoutHandler;
	
	public DsSvgOutputHandler() {
		this.dsLayoutHandler = new DsLayoutHandler<Double>(new DsDoubleCalculator());
		doc = Jsoup.parse("<svg></svg>");
	}
	
	@Override
	public void write(Writer writer, List<DsNode> startNodes, List<DsNode> dsSwimmers, double inputSum) throws IOException {
		logger.info("Startar bygge av svg...");

		// Init svg element and namespaces
	    Element svg = doc.select("svg").first();
	    svg.attr("xmlns", "http://www.w3.org/2000/svg");
	    svg.attr("xmlns:xlink", "http://www.w3.org/1999/xlink");
	    
	    // Invoke builder
	    writeImpl(svg,startNodes,dsSwimmers,inputSum);
	    
	    //Finally...
	    svg.attr("width", nstr(dsLayoutHandler.getOutputWidth()));
	    svg.attr("height", nstr(dsLayoutHandler.getOutputHeight()));
	    
	    //Output svg to writer
	    writer.write(doc.select("body").first().html());

	    logger.info("Bygge av svg klar");
	}


	protected final Document getDocument() {
		return this.doc;
	}

	/**
	 * Number as String
	 * @param i
	 * @return
	 */
	protected String nstr(int i) {
		return String.valueOf(i);
	}
	
	/**
	 * Number as String
	 * @param i
	 * @return
	 */
	private String nstr(double d) {
		return String.valueOf(d);
	}
	
	/**
	 * Create a rectangle
	 * @param width TODO
	 * @param height TODO
	 * @return
	 */
	protected Node createRectangle(double width, double height) {
		Node rectangle = new Element("rect");
	    rectangle.attr("width", nstr(width));
	    rectangle.attr("height", nstr(height));
	    rectangle.attr("style", "fill:rgb(120,146,255);stroke-width:1;stroke:rgb(0,0,0)");
	    return rectangle;
	}
	
	/**
	 * Position a node
	 * @param node
	 * @param x
	 * @param y
	 * @return
	 */
	protected Node setPosition(Node node, int x, int y) {
		node.attr("x", nstr(x));
		node.attr("y", nstr(y));
		return node;
	}



	/**
	 * Implementation
	 * @param svgRoot
	 */
	abstract void writeImpl(Element svgRoot, List<DsNode> startNodes, List<DsNode> dsSwimmers, double inputSum);
	
}
