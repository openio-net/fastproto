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
