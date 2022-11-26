/*
 * Licensed to the OpenIO.Net under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.openio.fastproto;

import net.openio.fastproto.config.Config;
import net.openio.fastproto.core_generator.ProtoGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.List;


@Mojo(
        name = "generate",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.COMPILE,
        threadSafe = true
)
public class FastProtoMojo extends AbstractMojo {


    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter( required = true)
    private String SourcesDir;

    @Parameter( required = true)
    private String javaOutDir;

    @Parameter( required = true)
    private List<String> protoFile;


    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        File baseDir = project.getBasedir();
        Config config=new Config();
        config.setJavaOut(new File(baseDir,javaOutDir).getPath());
        config.setFileDir(new File(baseDir,SourcesDir).getPath());
        config.addProtoFiles(protoFile);
        try {
            ProtoGenerator.generate(config);
            project.addCompileSourceRoot(config.getJavaOut());
        }catch (Exception e){
            getLog().error("Failed to generate fastproto code for " + protoFile + ": " + e.getMessage(), e);
            throw new MojoExecutionException("Failed to generate fastproto code for " + protoFile, e);
        }

    }
}
