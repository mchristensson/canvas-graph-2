package org.mac.canvasgraph.svg;

import org.jsoup.nodes.Element;

public class DsSvgOutputHandlerExample extends DsSvgOutputHandler {
	
	@Override
	 void writeImpl(Element svgRoot) {
		svgRoot.appendChild(createRectangle());
	}
}
