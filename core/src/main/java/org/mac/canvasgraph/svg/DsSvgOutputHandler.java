package org.mac.canvasgraph.svg;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.safety.Whitelist;
import org.mac.canvasgraph.ds.DsNode;
import org.mac.canvasgraph.ds.DsOutputHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DsSvgOutputHandler implements DsOutputHandler {

	private static final Logger logger = LoggerFactory.getLogger(DsSvgOutputHandler.class);
	private final Document doc;
	
	public DsSvgOutputHandler() {
		doc = Jsoup.parse("<svg></svg>");
	}
	
	@Override
	public void write(Writer writer, List<DsNode> startNodes, List<DsNode> dsSwimmers, double inputSum) throws IOException {
		logger.info("Startar bygge av svg...");

	    Element svg = doc.select("svg").first();
	    svg.attr("width", nstr(400));
	    svg.attr("height", nstr(300));
	    
	    writeImpl(svg);
	    
		String html = doc.select("body").first().html();
		logger.info("Bygge av svg klar. \n{}", html);
		writer.write(html);
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
	 * Create a rectangle
	 * @return
	 */
	protected Node createRectangle() {
		Node rectangle = new Element("rect");
	    rectangle.attr("width", nstr(200));
	    rectangle.attr("height", nstr(10));
	    rectangle.attr("style", "fill:rgb(0,0,255);stroke-width:3;stroke:rgb(0,0,0)");
	    return rectangle;
	}

	/**
	 * Implementation
	 * @param svgRoot
	 */
	abstract void writeImpl(Element svgRoot);
	
}
