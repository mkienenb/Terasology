/*
 * The copyright for these files are held by Richard aka OreSpawn aka TheyCallMeDanger
 */
package org.terasology.rendering.techne.example;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class ModelFrog extends ModelBase
{
    private float wingspeed = 1.0F;
      //fields
    ModelRenderer body;
    ModelRenderer jaw;
    ModelRenderer lfleg;
    ModelRenderer rfleg;
    ModelRenderer lleg1;
    ModelRenderer rleg1;
    ModelRenderer lleg2;
    ModelRenderer rleg2;
    ModelRenderer leye;
    ModelRenderer reye;
  
  public ModelFrog(float f1)
  {

    this.wingspeed = f1;
    
    textureWidth = 64;
    textureHeight = 64;
    
    body = new ModelRenderer(this, 41, 0);
    body.addBox(-4F, -10F, 0F, 8, 11, 2);
    body.setRotationPoint(0F, 24F, 2F);
    body.setTextureSize(64, 64);
    body.mirror = true;
    setRotation(body, 0.7330383F, 0F, 0F);
    
    jaw = new ModelRenderer(this, 42, 15);
    jaw.addBox(-4F, -8F, 0F, 8, 8, 1);
    jaw.setRotationPoint(0F, 24F, 2F);
    jaw.setTextureSize(64, 64);
    jaw.mirror = true;
    setRotation(jaw, 1.22173F, 0F, 0F);
    
    lfleg = new ModelRenderer(this, 14, 0);
    lfleg.addBox(0F, 0F, 0F, 1, 5, 1);
    lfleg.setRotationPoint(3F, 20F, 0F);
    lfleg.setTextureSize(64, 64);
    lfleg.mirror = true;
    setRotation(lfleg, -0.5235988F, 0F, -0.4712389F);
    
    rfleg = new ModelRenderer(this, 20, 0);
    rfleg.addBox(-1F, 0F, 0F, 1, 5, 1);
    rfleg.setRotationPoint(-3F, 20F, 0F);
    rfleg.setTextureSize(64, 64);
    rfleg.mirror = true;
    setRotation(rfleg, -0.5235988F, 0F, 0.4712389F);
    
    lleg1 = new ModelRenderer(this, 10, 8);
    lleg1.addBox(0F, -9F, -1F, 1, 9, 2);
    lleg1.setRotationPoint(3F, 24F, 3F);
    lleg1.setTextureSize(64, 64);
    lleg1.mirror = true;
    setRotation(lleg1, 0F, 0F, 0.2268928F);
    
    rleg1 = new ModelRenderer(this, 18, 8);
    rleg1.addBox(-1F, -9F, -1F, 1, 9, 2);
    rleg1.setRotationPoint(-3F, 24F, 3F);
    rleg1.setTextureSize(64, 64);
    rleg1.mirror = true;
    setRotation(rleg1, 0F, 0F, -0.2268928F);
    
    lleg2 = new ModelRenderer(this, 11, 20);
    lleg2.addBox(0F, 0F, 0F, 1, 10, 1);
    lleg2.setRotationPoint(5F, 15F, 3F);
    lleg2.setTextureSize(64, 64);
    lleg2.mirror = true;
    setRotation(lleg2, 0F, 0F, -0.3839724F);
    
    rleg2 = new ModelRenderer(this, 19, 20);
    rleg2.addBox(-1F, 0F, 0F, 1, 10, 1);
    rleg2.setRotationPoint(-5F, 15F, 3F);
    rleg2.setTextureSize(64, 64);
    rleg2.mirror = true;
    setRotation(rleg2, 0F, 0F, 0.3839724F);
    
    leye = new ModelRenderer(this, 0, 8);
    leye.addBox(0F, 0F, 0F, 1, 2, 1);
    leye.setRotationPoint(2F, 17F, -2F);
    leye.setTextureSize(64, 64);
    leye.mirror = true;
    setRotation(leye, 0.7330383F, 0F, 0F);
    
    reye = new ModelRenderer(this, 0, 4);
    reye.addBox(0F, 0F, 0F, 1, 2, 1);
    reye.setRotationPoint(-3F, 17F, -2F);
    reye.setTextureSize(64, 64);
    reye.mirror = true;
    setRotation(reye, 0.7330383F, 0F, 0F);
  }
  
  //f1 appears to be a scale of xyz motion.
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
	  Frog c = (Frog)entity;
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    float newangle = 0;
    //System.out.printf("floats: %f,  %f, %f, %f, %f, %f\n", f, f1, f2, f3, f4, f5);
    if(f1 > 0.1){
    	newangle = MathHelper.cos(f2 * this.wingspeed * 1.4f ) * (float)Math.PI * 0.55F * f1;
    }else{
    	newangle = 0.0F;
    }
    lfleg.rotateAngleY = newangle;
    rfleg.rotateAngleY = -newangle;
    lleg2.rotateAngleY = -newangle/2;
    rleg2.rotateAngleY = newangle/2;

    
    if(c.getSinging() != 0){
    	newangle = MathHelper.cos(f2 * 0.85f * this.wingspeed ) * (float)Math.PI * 0.15F;
    }else{
    	newangle = 0;
    }
    jaw.rotateAngleX = newangle + 1.22f;
    
    if(c.motionY > 0.1f || c.motionY < -0.1f){
    	lleg1.rotateAngleZ = 2.44f;
    	rleg1.rotateAngleZ = -2.44f;
    }else{
    	lleg1.rotateAngleZ = 0.227f;
    	rleg1.rotateAngleZ = -0.227f;
    }
    lleg2.rotationPointY = lleg1.rotationPointY - (float)Math.cos(lleg1.rotateAngleZ)*9;
    lleg2.rotationPointX = lleg1.rotationPointX + (float)Math.sin(lleg1.rotateAngleZ)*9;  
    
    rleg2.rotationPointY = rleg1.rotationPointY - (float)Math.cos(rleg1.rotateAngleZ)*9;
    rleg2.rotationPointX = rleg1.rotationPointX + (float)Math.sin(rleg1.rotateAngleZ)*9;  
    
    body.render(f5);
    jaw.render(f5);
    lfleg.render(f5);
    rfleg.render(f5);
    lleg1.render(f5);
    rleg1.render(f5);
    lleg2.render(f5);
    rleg2.render(f5);
    leye.render(f5);
    reye.render(f5);
    
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
  {
    super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
  }
  

}

