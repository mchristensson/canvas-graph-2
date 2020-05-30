package org.mac.canvasgraph.svg;

import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.mac.canvasgraph.ds.DsNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DsSvgOutputHandlerExample extends DsSvgOutputHandler {
	private static final Logger logger = LoggerFactory.getLogger(DsSvgOutputHandlerExample.class);

	@Override
	void writeImpl(Element svgRoot, List<DsNode> startNodes, List<DsNode> dsSwimmers, double inputSum) {
		logger.info("WriteImpl BEGIN");
		
		dsLayoutHandler.setMarginY(20.0);
		dsLayoutHandler.setMarginX(20.0);
		dsLayoutHandler.setDefaultUnitLength(100.0);
		dsLayoutHandler.setChannelPadding(30.0);
		
		dsLayoutHandler.addUnit();
		for (Iterator<DsNode> iterator = startNodes.iterator(); iterator.hasNext();) {

			DsNode dsNode = (DsNode) iterator.next();
			double value = dsNode.getValue();
			dsLayoutHandler.addChannel(value);
			
			Node node = createRectangle(dsLayoutHandler.getDefaultUnitLength(), value);
			setPosition(node, dsLayoutHandler.getWidthAsInt(), dsLayoutHandler.getHeightAsInt());
			svgRoot.appendChild(node);
		}
		
		logger.info("WriteImpl END");
		logger.info(dsLayoutHandler.toString());
	}

}
