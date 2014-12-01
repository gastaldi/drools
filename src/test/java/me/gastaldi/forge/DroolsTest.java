package me.gastaldi.forge;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.Dependencies;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.jboss.forge.furnace.repositories.AddonDependencyEntry;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

@RunWith(Arquillian.class)
public class DroolsTest {

	@Deployment
	@Dependencies({
			@AddonDependency(name = "org.jboss.forge.furnace.container:cdi"),
			@AddonDependency(name = "me.gastaldi.forge:drools") })
	public static ForgeArchive getDeployment() {
		ForgeArchive archive = ShrinkWrap
				.create(ForgeArchive.class)
				.addBeansXML()
				.addAsManifestResource(
						new File("src/main/resources/META-INF/kmodule.xml"))
				.addAsAddonDependencies(
						AddonDependencyEntry
								.create("org.jboss.forge.furnace.container:cdi"),
						AddonDependencyEntry.create("me.gastaldi.forge:drools"));
		return archive;
	}

	@Test
	public void testInjection() throws Exception {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession1");
		Assert.assertNotNull(kSession);
	}
}