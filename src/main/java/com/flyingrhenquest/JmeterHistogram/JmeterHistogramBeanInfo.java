/**
 * UI For Histogram Generator
 *
 * Copyright 2011 Bruce Ide
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.flyingrhenquest.JmeterHistogram;

import java.beans.PropertyDescriptor;
import org.apache.jmeter.testbeans.BeanInfoSupport;

public class JmeterHistogramBeanInfo extends BeanInfoSupport {

  public JmeterHistogramBeanInfo() {
    super(JmeterHistogram.class);

    createPropertyGroup("Histogram Buckets",
                        new String[] {
                          "histogramRedBucket",
                          "histogramGreenBucket",
                          "histogramBlueBucket"
                        });

    createPropertyGroup("Nonzero Indicators",
                        new String[] {
                          "nonzeroRed",
                          "nonzeroGreen",
                          "nonzeroBlue"
                        });

    createPropertyGroup("Image Found Indicator",
                        new String[] {
                          "imageFound"
                        });
    
    PropertyDescriptor p;
    p = property("histogramRedBucket");
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "red_pixels");

    p = property("histogramGreenBucket");
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "green_pixels");
    
    p = property("histogramBlueBucket");
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "blue_pixels");
    
    p = property("nonzeroRed");
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "nonzero_red");

    p = property("nonzeroGreen");
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "nonzero_green");
    
    p = property("nonzeroBlue");
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "nonzero_blue");

    p = property("imageFound");
    p.setValue(NOT_UNDEFINED, Boolean.TRUE);
    p.setValue(DEFAULT, "image_found");
  }

}