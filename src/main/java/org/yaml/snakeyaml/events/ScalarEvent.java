/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.Mark;

/**
 * @see <a href="http://pyyaml.org/wiki/PyYAML">PyYAML</a> for more information
 */
public final class ScalarEvent extends NodeEvent {
    private final String tag;
    // style flag of a scalar event indicates the style of the scalar. Possible
    // values are None, '', '\'', '"', '|', '>'
    private final Character style;
    private final String value;
    // The implicit flag of a scalar event is a pair of boolean values that
    // indicate if the tag may be omitted when the scalar is emitted in a plain
    // and non-plain style correspondingly.
    //TODO should we use tuple ?
    private final boolean[] implicit;

    public ScalarEvent(String anchor, String tag, boolean[] implicit, String value, Mark startMark,
            Mark endMark, Character style) {
        super(anchor, startMark, endMark);
        this.tag = tag;
        this.implicit = implicit;
        this.value = value;
        this.style = style;
    }

    public String getTag() {
        return this.tag;
    }

    public Character getStyle() {
        return this.style;
    }

    public String getValue() {
        return this.value;
    }

    public boolean[] getImplicit() {
        return this.implicit;
    }

    @Override
    protected String getArguments() {
        return super.getArguments() + ", tag=" + tag + ", implicit=[" + implicit[0] + ", "
                + implicit[1] + "], value=" + value;
    }

}
