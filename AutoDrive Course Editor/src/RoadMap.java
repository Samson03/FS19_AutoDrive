import java.util.LinkedList;
import java.util.Map;

public class RoadMap {

    public LinkedList<MapNode> mapNodes;
    public LinkedList<MapMarker> mapMarkers;

    public RoadMap () {
        this.mapMarkers = new LinkedList<>();
        this.mapNodes = new LinkedList<>();
    }

    public void addMapNode(MapNode mapNode) {
        this.mapNodes.add(mapNode);
    }

    public void addMapMarker(MapMarker mapMarker) {
        this.mapMarkers.add(mapMarker);
    }

    public void removeMapNode(MapNode toDelete) {
        boolean deleted = false;
        if (mapNodes.contains(toDelete)) {
            mapNodes.remove(toDelete);
            deleted = true;
        }

        for (MapNode mapNode : this.mapNodes) {
            if (mapNode.outgoing.contains(toDelete)) {
                mapNode.outgoing.remove(toDelete);
            }
            if (mapNode.incoming.contains(toDelete)) {
                mapNode.incoming.remove(toDelete);
            }
            if (deleted && mapNode.id > toDelete.id) {
                mapNode.id--;
            }
        }

        LinkedList<MapMarker> mapMarkersToDelete = new LinkedList<>();
        for (MapMarker mapMarker : this.mapMarkers) {
            if (mapMarker.mapNode == toDelete) {

                mapMarkersToDelete.add(mapMarker);
            }
        }
        for (MapMarker mapMarker : mapMarkersToDelete) {
            removeMapMarker(mapMarker);
            this.mapMarkers.remove(mapMarker);
        }
    }

    public void removeMapMarker(MapMarker mapMarker) {
        for (MapNode mapNode : this.mapNodes) {
            LinkedList<MapMarker> toDeleteList = new LinkedList<>();
            for (Map.Entry<MapMarker,MapNode>  entry : mapNode.directions.entrySet() ) {
                if (entry.getKey().name.equals(mapMarker.name)) {
                    toDeleteList.add(entry.getKey());
                }
            }
            if (toDeleteList.size() > 0 ) {
                mapNode.directions.entrySet().removeIf(e -> (e.getKey().name.equals(mapMarker.name)));
            }
        }
    }

    public static boolean isDual(MapNode start, MapNode target) {
        for (MapNode outgoing : start.outgoing) {
            if (outgoing == target) {
                for (MapNode outgoingTarget : target.outgoing) {
                    if (outgoingTarget == start) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
