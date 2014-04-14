/*
 * ARX: Efficient, Stable and Optimal Data Anonymization
 * Copyright (C) 2014 Karol Babioch <karol@babioch.de>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.deidentifier.arx.examples;

import java.io.IOException;

import org.deidentifier.arx.ARXAnonymizer;
import org.deidentifier.arx.ARXConfiguration;
import org.deidentifier.arx.ARXResult;
import org.deidentifier.arx.AttributeType.Hierarchy;
import org.deidentifier.arx.Data;
import org.deidentifier.arx.DataType;
import org.deidentifier.arx.criteria.KAnonymity;
import org.deidentifier.arx.io.datasource.ExcelFileConfiguration;
import org.deidentifier.arx.io.datasource.column.ExcelColumn;

/**
 * This class demonstrates how to use the API to import data from Excel (XLS
 * or XLSX) files. It is loosely based upon {@link Example2a} as the API is
 * quite similar.
 */
public class Example2b extends Example {

    /**
     * Main entry point
     */
    public static void main(final String[] args) throws IOException {

        try {

            // Define configuration for Excel file(s)
            ExcelFileConfiguration importConfig = new ExcelFileConfiguration("data/test.xls", 0, true);
            //ExcelFileConfiguration importConfig = new ExcelFileConfiguration("data/test.xlsx", 0, true);

            // Add columns (index, name and datatype) to configuration
            // The name is optional and can be detected/assigned automatically
            importConfig.addColumn(new ExcelColumn(0, "Alter", DataType.INTEGER));
            importConfig.addColumn(new ExcelColumn(1, DataType.STRING));
            importConfig.addColumn(new ExcelColumn(2, DataType.STRING));

            // Create data object
            final Data data = Data.create(importConfig);

            // Define attribute hierarchies
            data.getDefinition()
                .setAttributeType("Alter",
                                  Hierarchy.create("data/test_hierarchy_age.csv",
                                                   ';'));
            data.getDefinition()
                .setAttributeType("gender",
                                  Hierarchy.create("data/test_hierarchy_gender.csv",
                                                   ';'));
            data.getDefinition()
                .setAttributeType("zipcode",
                                  Hierarchy.create("data/test_hierarchy_zipcode.csv",
                                                   ';'));

            // Create an instance of the anonymizer
            final ARXAnonymizer anonymizer = new ARXAnonymizer();

            // Execute the algorithm
            final ARXConfiguration config = ARXConfiguration.create();
            config.addCriterion(new KAnonymity(2));
            config.setMaxOutliers(0d);
            final ARXResult result = anonymizer.anonymize(data, config);

            // Print info
            printResult(result, data);

            // Write results
            System.out.print(" - Writing data...");
            result.getOutput(false).save("data/test_anonymized.csv", ';');
            System.out.println("Done!");

        } catch (final IllegalArgumentException e) {

            throw new RuntimeException(e);

        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }

}
