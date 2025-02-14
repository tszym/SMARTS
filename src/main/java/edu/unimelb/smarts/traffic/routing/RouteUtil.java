package edu.unimelb.smarts.traffic.routing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import edu.unimelb.smarts.common.Settings;
import edu.unimelb.smarts.processor.communication.message.SerializableRouteLeg;
import edu.unimelb.smarts.traffic.road.Edge;
import edu.unimelb.smarts.traffic.road.Lane;
import edu.unimelb.smarts.traffic.road.Node;
import edu.unimelb.smarts.traffic.road.RoadType;
import edu.unimelb.smarts.traffic.vehicle.VehicleType;

public class RouteUtil {

	public static RouteLeg createRouteLeg(final int edgeIndex, final double stopDuration, final ArrayList<Edge> edges) {
		return new RouteLeg(edges.get(edgeIndex), stopDuration);
	}

	static Node findRepeatedNode(final ArrayList<RouteLeg> routeLegs) {
		final HashMap<Integer, Integer> indexList = new HashMap<>();
		indexList.put(routeLegs.get(0).edge.startNode.index, routeLegs.get(0).edge.startNode.index);
		for (int i = 1; i < routeLegs.size(); i++) {
			final RouteLeg leg = routeLegs.get(i);
			if (indexList.containsKey(leg.edge.endNode.index)) {
				return leg.edge.endNode;
			} else {
				indexList.put(leg.edge.endNode.index, leg.edge.endNode.index);
			}
		}
		return null;
	}

	public static Routing.Algorithm getRoutingAlgorithmFromString(final String selected) {
		for (final Routing.Algorithm algorithm : Routing.Algorithm.values()) {
			if (selected.equalsIgnoreCase(algorithm.name())) {
				return algorithm;
			}
		}
		return Routing.Algorithm.DIJKSTRA;
	}

	public static ArrayList<RouteLeg> parseReceivedRoute(final ArrayList<SerializableRouteLeg> routeLegs,
			final ArrayList<Edge> edges) {
		final ArrayList<RouteLeg> route = new ArrayList<>();
		for (final SerializableRouteLeg routeLeg : routeLegs) {
			route.add(createRouteLeg(routeLeg.edgeIndex, routeLeg.stopover, edges));
		}
		return route;
	}

	public static ArrayList<RouteLeg> removeRepeatSections(final ArrayList<RouteLeg> routeLegs) {
		Node repeatedNode = findRepeatedNode(routeLegs);
		while (repeatedNode != null) {
			final Iterator<RouteLeg> iterator = routeLegs.iterator();
			boolean isToRemove = false;
			while (iterator.hasNext()) {
				final RouteLeg leg = iterator.next();
				if (repeatedNode == leg.edge.startNode) {
					isToRemove = true;
				}
				if (isToRemove) {
					iterator.remove();
					if (leg.edge.endNode == repeatedNode) {
						break;
					}
				}
			}
			if (routeLegs.size() == 0) {
				return null;
			}
			repeatedNode = findRepeatedNode(routeLegs);
		}

		return routeLegs;

	}
}
