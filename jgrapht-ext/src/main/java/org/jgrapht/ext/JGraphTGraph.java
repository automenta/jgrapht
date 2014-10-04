package org.jgrapht.ext;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.wrappers.WrapperGraph;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;

/**
 * An implementation of JGraphT interface provided by Blueprints graph.
 * In this way, a Blueprints graph is modeled as a JGraphT
 * This JGraphT model can be used with any algorithms/tools that require a JGraphT graph.
 * 
 * NOT tested yet.
 */
public class JGraphTGraph<T extends com.tinkerpop.blueprints.Graph> implements DirectedGraph<Vertex, Edge>, WrapperGraph<T> {

    public final T graph;

    public JGraphTGraph(T graph) {
        this.graph = graph;
    }

    final int size(final Iterable ii) {
        if (ii instanceof Collection) {
            return ((Collection)ii).size();
        }
        Iterator i = ii.iterator();
        int count = 0;
        while (i.hasNext()) { count++; i.next(); }
        return count;
    }

    @Override
    public Spliterator<Edge> edges() {
        return graph.getEdges().spliterator();
    }

    @Override
    public Spliterator<Vertex> vertices() {
        return graph.getVertices().spliterator();
    }
    
    
    @Override
    public int inDegreeOf(Vertex vertex) {        
        return size( vertex.getVertices(Direction.IN) );
    }
    
    public static <X> Set<X> iteratorToSet(final Iterator<X> i) {
        Set<X> s = new HashSet();
        while (i.hasNext()) {
            s.add(i.next());
        }
        return s;
    }
    public static <X> Set<X> iterableToSet(final Iterable<X> i) {
        if (i instanceof Set)
            return (Set<X>) i;
        return iteratorToSet(i.iterator());
    }

    @Override
    public Set<Edge> incomingEdgesOf(Vertex vertex) {
        return iterableToSet(vertex.getEdges(Direction.IN));
    }

    @Override
    public int outDegreeOf(Vertex vertex) {
        return size( vertex.getVertices(Direction.OUT) );
    }

    @Override
    public Set<Edge> outgoingEdgesOf(Vertex vertex) {
        return iterableToSet(vertex.getEdges(Direction.OUT));
    }

    @Override
    public Set<Edge> getAllEdges(Vertex sourceVertex, Vertex targetVertex) {        
        Set<Edge> e = incomingEdgesOf(sourceVertex);
        e.retainAll(outgoingEdgesOf(targetVertex));
        return e;
    }

    @Override
    public Edge getEdge(Vertex sourceVertex, Vertex targetVertex) {
        Iterator<Edge> i = sourceVertex.getEdges(Direction.OUT).iterator();
        while (i.hasNext()) {
            Edge e = i.next();
            if (e.getVertex(Direction.OUT).equals(targetVertex)) { //TODO check direction
                return e;
            }
        }
        return null;
    }

    @Override
    public EdgeFactory<Vertex, Edge> getEdgeFactory() {
        return new EdgeFactory() {
            @Override public Object createEdge(Object sourceVertex, Object targetVertex) {
                throw new UnsupportedOperationException("Not supported yet.");
            }       
        };
    }

    @Override
    public Edge addEdge(Vertex sourceVertex, Vertex targetVertex) {
        throw new UnsupportedOperationException("Not supported yet.");    
    }

    @Override
    public boolean addEdge(Vertex sourceVertex, Vertex targetVertex, Edge e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addVertex(Vertex v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsEdge(Vertex sourceVertex, Vertex targetVertex) {
        return getEdge(sourceVertex, targetVertex)!=null;
    }

    @Override
    public boolean containsEdge(Edge e) {
        return graph.getEdge(e.getId())!=null;
    }

    @Override
    public boolean containsVertex(Vertex v) {
        return graph.getVertex(v.getId())!=null;
    }

    @Override
    public Set<Edge> edgeSet() {
        return iterableToSet(graph.getEdges());
    }

    @Override
    public Set<Edge> edgesOf(Vertex vertex) {
        Set<Edge> e = incomingEdgesOf(vertex);
        e.addAll(outgoingEdgesOf(vertex));
        return e;
    }

    @Override
    public boolean removeAllEdges(Collection<? extends Edge> edges) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Edge> removeAllEdges(Vertex sourceVertex, Vertex targetVertex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAllVertices(Collection<? extends Vertex> vertices) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Edge removeEdge(Vertex sourceVertex, Vertex targetVertex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeEdge(Edge e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeVertex(Vertex v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<Vertex> vertexSet() {
        return iterableToSet(graph.getVertices());
    }

    @Override
    public Vertex getEdgeSource(Edge e) {
        return e.getVertex(Direction.IN); //TODO check direction
    }

    @Override
    public Vertex getEdgeTarget(Edge e) {
        return e.getVertex(Direction.OUT); //TODO check direction
    }

    @Override
    public double getEdgeWeight(Edge e) {
        return 1;
    }

    @Override
    public T getBaseGraph() {
        return graph;
    }    
    
}
