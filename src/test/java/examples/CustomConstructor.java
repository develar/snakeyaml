/**
 * Copyright (c) 2008-2009 Andrey Somov
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
package examples;

import java.util.ArrayList;
import java.util.List;

import org.yaml.snakeyaml.Invoice;
import org.yaml.snakeyaml.constructor.Constructor;

public class CustomConstructor extends Constructor {

    public CustomConstructor() {
        super(Invoice.class);
    }

    @Override
    protected List<Object> createDefaultList(int initSize) {
        return new ArrayList<Object>(initSize);
    }
}