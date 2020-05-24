package org.mac.canvasgraph.ds;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface DsOutputHandler {

	void write(Writer writer, List<DsNode> startNodes, List<DsNode> dsSwimmers, double inputSum) throws IOException;

}
