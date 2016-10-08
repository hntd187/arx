/*
 * ARX: Powerful Data Anonymization
 * Copyright 2012 - 2016 Fabian Prasser, Florian Kohlmayer and contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deidentifier.arx.risk.msu;


public class Test {

    public static void main(String[] args) {
        
        // Rank (0,0) => Support 4
        // Rank (2,0) => Support 4
        // Rank (4,2) => Support 3
        
        // Itemset: (2,0), (0, 0)
        // Reference: (4,2)

        // (4,2) (2,0) (0,0) eigentlich abgedeckt durch (4,2) (0,0)
        int[][] data = new int[][] {
                new int[] { 0, 3, 0, 1, 1 }, // 0 
                new int[] { 0, 3, 0, 0, 1 }, // 1 
                new int[] { 0, 3, 1, 1, 1 }, // 2
                new int[] { 1, 3, 0, 1, 2 }, // 3 
                new int[] { 0, 2, 0, 1, 2 }, // 4 (0, -, 0, -, 2) -> (0, -, -, -,2)
                new int[] { 1, 2, 1, 0, 2 }  // 5
        };
        
        System.out.println("26 is correct");

        SUDA2 suda2 = new SUDA2(data);
        System.out.println(suda2.suda2(0));

        ExhaustiveSearch exhaustive = new ExhaustiveSearch(data);
        System.out.println(exhaustive.exhaustive());
    }
       
}