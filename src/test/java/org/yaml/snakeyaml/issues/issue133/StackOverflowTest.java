/**
 * Copyright (c) 2008, http://www.snakeyaml.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yaml.snakeyaml.issues.issue133;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

/**
 * to test http://code.google.com/p/snakeyaml/issues/detail?id=133
 */
public class StackOverflowTest {

    @Test
    public void testDumpRecursiveObjectOnJava6() {

        org.junit.Assume.assumeTrue(System.getProperty("java.version").startsWith("1.6"));

        try {
            Yaml yaml = new Yaml();
            // by default it must fail with StackOverflow
            yaml.dump(new Point());
            fail("getLocation() is recursive.");
        } catch (Throwable e) {
            String message = e.getMessage();
            assertTrue("StackOverflow has no message: " + e.getMessage(), message == null
                    || message.contains("Unable to find getter for property 'location'"));
        }
    }

    @Test
    public void testDumpWithTrasitivePropertySinceJava7() {

        org.junit.Assume.assumeFalse(System.getProperty("java.version").startsWith("1.6"));

        Yaml yaml = new Yaml();

        Point point = new Point(1, 2);
        String output = yaml.dump(point);
        assertEquals("!!java.awt.Point {x: 1, y: 2}\n", output);
    }

    /**
     * Since Point.getLocation() creates a new instance of Point class,
     * SnakeYAML will fail to dump an instance of Point if 'getLocation()' is
     * also included.
     *
     * Since Point is not really a JavaBean, we can safely skip the recursive
     * property when we dump the instance of Point.
     */
    private class PointRepresenter extends Representer {

        @Override
        protected NodeTuple representJavaBeanProperty(Object javaBean, Property property,
                Object propertyValue, Tag customTag) {
            if (javaBean instanceof Point && "location".equals(property.getName())) {
                return null;
            } else {
                return super.representJavaBeanProperty(javaBean, property, propertyValue,
                        customTag);
            }
        }
    }

    @Test
    public void testDump() {
        Yaml yaml = new Yaml(new PointRepresenter());
        String output = yaml.dump(new Point());
        assertEquals("!!java.awt.Point {x: 0, y: 0}\n", output);
    }
}
