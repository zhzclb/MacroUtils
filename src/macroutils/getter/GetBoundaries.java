package macroutils.getter;

import java.util.ArrayList;
import java.util.Vector;
import macroutils.MacroUtils;
import star.common.Boundary;
import star.common.BoundaryInterface;
import star.common.InterfaceBoundary;
import star.common.Region;

/**
 * Low-level class for getting Boundaries with MacroUtils.
 *
 * @since April of 2016
 * @author Fabio Kasper
 */
public class GetBoundaries {

    /**
     * Main constructor for this class.
     *
     * @param m given MacroUtils object.
     */
    public GetBoundaries(MacroUtils m) {
        _mu = m;
    }

    /**
     * Gets all Boundaries from all Regions available in the model.
     *
     * @param vo given verbose option. False will not print anything.
     * @return An ArrayList of Boundaries.
     */
    public ArrayList<Boundary> all(boolean vo) {
        ArrayList<Boundary> ab = new ArrayList<>();
        _io.say.msg(vo, "Getting all Boundaries from all Regions...");
        for (Region r : _get.regions.all(false)) {
            ab.addAll(r.getBoundaryManager().getBoundaries());
        }
        _io.say.objects(ab, "Boundaries", vo);
        return ab;
    }

    /**
     * Gets all Boundaries from a given Region.
     *
     * @param r given Region.
     * @param vo given verbose option. False will not print anything.
     * @return An ArrayList of Boundaries.
     */
    public ArrayList<Boundary> all(Region r, boolean vo) {
        _io.say.value("Getting all Boundaries from Region", r.getPresentationName(), true, vo);
        ArrayList<Boundary> ab = new ArrayList<>(r.getBoundaryManager().getBoundaries());
        _io.say.objects(ab, "Boundaries", vo);
        return ab;
    }

    /**
     * Gets all Boundaries that matches the REGEX search pattern from all Regions available in the model.
     *
     * @param regexPatt given Regular Expression (REGEX) pattern.
     * @param vo given verbose option. False will not print anything.
     * @return An ArrayList of Boundaries.
     */
    public ArrayList<Boundary> allByREGEX(String regexPatt, boolean vo) {
        return new ArrayList<>(_get.objects.allByREGEX(regexPatt, "all Boundaries", new ArrayList<>(all(false)), vo));
    }

    /**
     * Gets a Boundary that matches the REGEX search pattern among all Boundaries available in the model.
     *
     * @param regexPatt given Regular Expression (REGEX) pattern.
     * @param vo given verbose option. False will not print anything.
     * @return The Boundary. Null if nothing is found.
     */
    public Boundary byREGEX(String regexPatt, boolean vo) {
        return (Boundary) _get.objects.byREGEX(regexPatt, "Boundary", new ArrayList<>(all(false)), vo);
    }

    /**
     * Gets a Boundary within a Region that matches the REGEX search pattern.
     *
     * @param r given Region.
     * @param regexPatt given Regular Expression (REGEX) pattern.
     * @param vo given verbose option. False will not print anything.
     * @return The Boundary. Null if nothing is found.
     */
    public Boundary byREGEX(Region r, String regexPatt, boolean vo) {
        return (Boundary) _get.objects.byREGEX(regexPatt, "Boundary",
                new ArrayList<>(r.getBoundaryManager().getBoundaries()), vo);
    }

    /**
     * Gets the first interface associated to a Boundary.
     *
     * @param b given Boundary.
     * @param vo given verbose option. False will not print anything.
     * @return The InterfaceBoundary. Null if nothing is found.
     */
    public InterfaceBoundary interfaceBoundary(Boundary b, boolean vo) {
        _io.print.msg(vo, "Getting the InterfaceBoundary associated to Boundary: \"%s\".", b.getPresentationName());
        Vector<BoundaryInterface> vbi = b.getDependentInterfaces();
        if (vbi.isEmpty()) {
            _io.print.msg("No Interfaces found. Returning NULL!", vo);
            return null;
        }
        InterfaceBoundary ib = vbi.get(0).getInterfaceBoundary0();
        _io.say.value("Found", ib.getPresentationName(), true, vo);
        return ib;
    }

    /**
     * This method is called automatically by {@link MacroUtils}.
     */
    public void updateInstances() {
        _get = _mu.get;
        _io = _mu.io;
    }

    //--
    //-- Variables declaration area.
    //--
    private MacroUtils _mu = null;
    private MainGetter _get = null;
    private macroutils.io.MainIO _io = null;

}
