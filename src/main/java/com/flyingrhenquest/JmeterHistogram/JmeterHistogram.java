/**
 * Jmeter Histogram Extractor - Extracts a histogram into jmeter vars 
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
 */

package com.flyingrhenquest.JmeterHistogram;

import org.apache.jmeter.processor.PostProcessor;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testbeans.TestBean;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;

import java.io.ByteArrayInputStream;
import javax.media.jai.JAI;
import javax.media.jai.Histogram;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.RenderedOp;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class JmeterHistogram extends AbstractTestElement implements PostProcessor, TestBean {
  public static final long serialVersionUID = 240l;

  /**
   * Names of buckets where image intensities will be dumped. Each will
   * be an array from 0 to 255 of intensities. Each bucket will contain
   * a count of pixels at that intensity.
   */

  private String histogramRedBucket;
  private String histogramGreenBucket;
  private String histogramBlueBucket;

  /**
   * nonzero buckets, for red, green and blue, will store the number
   * of intensities that had a non-zero count.
   */

  private String nonzeroRed;
  private String nonzeroGreen;
  private String nonzeroBlue;

  /**
   * Variable imageFound is written with true if the sampleresult
   * had an image, or false if it didn't or JAI couldn't read it.
   */

  private String imageFound;

  /**
   * Variable to store histogram number of bytes in
   */

  private static final String histogramNbytes = "histogram_nbytes";

  /**
   * Grabs an image from a sampler and generates a histogram using
   * JAI. Then it writes all the histogram data into variables.
   */

  public void process() {
    JMeterContext context = getThreadContext();
    JMeterVariables vars = context.getVariables();
    SampleResult previousResult = context.getPreviousResult();
    ByteArrayInputStream resultStream = new ByteArrayInputStream(previousResult.getResponseData());
    vars.put(histogramNbytes, Integer.toString(previousResult.getBytes()));
    BufferedImage image = null;
    try {
      image = ImageIO.read(resultStream);
    } catch (java.io.IOException e) {
      vars.put(imageFound, "false");
      return; // May as well stop here...
    }
    if (null == image) {
      // Not an image
      vars.put(imageFound, "false");
      return;
    }
    ParameterBlock pb = new ParameterBlock();

    pb.addSource(image);
    RenderedOp op = JAI.create("histogram", pb, null);
    Histogram hist = (Histogram) op.getProperty("histogram");
    int redBuckets = 0;
    int greenBuckets = 0;
    int blueBuckets = 0;
    for (int i = 0; i < hist.getNumBins()[0]; i++) {
      int red = hist.getBinSize(0, i);
      int green = hist.getBinSize(1, i);
      int blue = hist.getBinSize(2, i);
      String counter = Integer.toString(i);
      if (red > 0) {
        redBuckets++;
      }
      if (green > 0) {
        greenBuckets++;
      }
      if (blue > 0) {
        blueBuckets++;
      }
      vars.put(histogramRedBucket + "_" + counter, Integer.toString(red));
      vars.put(histogramGreenBucket + "_" + counter, Integer.toString(green));
      vars.put(histogramBlueBucket + "_" + counter, Integer.toString(blue));
    }
    vars.put(nonzeroRed, Integer.toString(redBuckets));
    vars.put(nonzeroGreen, Integer.toString(greenBuckets));
    vars.put(nonzeroBlue, Integer.toString(blueBuckets));
    vars.put(imageFound, "true");
  }

  public String getNonzeroRed() {
    return nonzeroRed;
  }

  public void setNonzeroRed(String nonzeroRed) {
    this.nonzeroRed = nonzeroRed;
  }

  public String getNonzeroGreen() {
    return nonzeroGreen;
  }

  public void setNonzeroGreen(String nonzeroGreen) {
    this.nonzeroGreen = nonzeroGreen;
  }

  public String getNonzeroBlue() {
    return nonzeroBlue;
  }

  public void setNonzeroBlue(String nonzeroBlue) {
    this.nonzeroBlue = nonzeroBlue;
  }

  public String getHistogramRedBucket() {
    return histogramRedBucket;
  }

  public void setHistogramRedBucket(String histogramRedBucket) {
    this.histogramRedBucket = histogramRedBucket;
  }
  
  public String getHistogramGreenBucket() {
    return histogramGreenBucket;
  }

  public void setHistogramGreenBucket(String histogramGreenBucket) {
    this.histogramGreenBucket = histogramGreenBucket;
  }

  public String getHistogramBlueBucket() {
    return histogramBlueBucket;
  }

  public void setHistogramBlueBucket(String histogramBlueBucket) {
    this.histogramBlueBucket = histogramBlueBucket;
  }

  public String getImageFound() {
    return imageFound;
  }

  public void setImageFound(String imageFound) {
    this.imageFound = imageFound;
  }

}