/*
 * These files are owned by Richard aka OreSpawn aka TheyCallMeDanger
 */
package org.terasology.rendering.techne.example;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
// import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;

public class ModelAlosaurus extends ModelBase
{
    private float wingspeed = 1.0F;
      //fields
    ModelRenderer Shape18;
    ModelRenderer Shape19;
    ModelRenderer Shape20;
    ModelRenderer Shape21;
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape6;
    ModelRenderer jaw;
    ModelRenderer leftleg;
    ModelRenderer leftleg2;
    ModelRenderer leftleg3;
    ModelRenderer Shape11;
    ModelRenderer rightleg;
    ModelRenderer rightleg2;
    ModelRenderer rightleg3;
    ModelRenderer leftleg4;
    ModelRenderer rightleg4;
    ModelRenderer Shape17;
  
  public ModelAlosaurus(float f1)
  {
    textureWidth = 128;
    textureHeight = 128;
    this.wingspeed = f1;
    
    Shape18 = new ModelRenderer(this, 91, 114);
    Shape18.addBox(0F, 0F, 0F, 2, 4, 5);
    Shape18.setRotationPoint(3.3F, -25F, -27F);
    Shape18.setTextureSize(128, 128);
    Shape18.mirror = true;
    setRotation(Shape18, 0.5759587F, 0F, 0.5585054F);
    Shape19 = new ModelRenderer(this, 71, 114);
    Shape19.addBox(0F, 0F, 0F, 2, 4, 5);
    Shape19.setRotationPoint(-4F, -24F, -28F);
    Shape19.setTextureSize(128, 128);
    Shape19.mirror = true;
    setRotation(Shape19, 0.5759587F, 0F, -0.5585054F);
    Shape20 = new ModelRenderer(this, 91, 30);
    Shape20.addBox(0F, 0F, 0F, 2, 7, 5);
    Shape20.setRotationPoint(5F, -8F, -6F);
    Shape20.setTextureSize(128, 128);
    Shape20.mirror = true;
    setRotation(Shape20, 0.3839724F, 0F, 0F);
    Shape21 = new ModelRenderer(this, 93, 46);
    Shape21.addBox(-2F, 0F, 0F, 2, 7, 5);
    Shape21.setRotationPoint(-4F, -8F, -6F);
    Shape21.setTextureSize(128, 128);
    Shape21.mirror = true;
    setRotation(Shape21, 0.3839724F, 0F, 0F);
    Shape1 = new ModelRenderer(this, 0, 0);
    Shape1.addBox(-7F, 0F, 0F, 10, 18, 31);
    Shape1.setRotationPoint(2.5F, -19F, -8F);
    Shape1.setTextureSize(128, 128);
    Shape1.mirror = true;
    setRotation(Shape1, 0F, 0F, 0F);
    Shape2 = new ModelRenderer(this, 62, 0);
    Shape2.addBox(-5F, 0F, 0F, 10, 11, 11);
    Shape2.setRotationPoint(0.5F, -19F, 23F);
    Shape2.setTextureSize(128, 128);
    Shape2.mirror = true;
    setRotation(Shape2, 0F, 0F, 0F);
    Shape3 = new ModelRenderer(this, 10, 54);
    Shape3.addBox(-3F, 0F, 0F, 7, 7, 25);
    Shape3.setRotationPoint(0F, -19F, 34F);
    Shape3.setTextureSize(128, 128);
    Shape3.mirror = true;
    setRotation(Shape3, 0F, 0F, 0F);
    Shape4 = new ModelRenderer(this, 68, 88);
    Shape4.addBox(-5F, 0F, 0F, 8, 9, 16);
    Shape4.setRotationPoint(1.5F, -25F, -16F);
    Shape4.setTextureSize(128, 128);
    Shape4.mirror = true;
    setRotation(Shape4, -0.4014257F, 0F, 0F);
    Shape5 = new ModelRenderer(this, 75, 65);
    Shape5.addBox(0F, 0F, 0F, 9, 9, 12);
    Shape5.setRotationPoint(-4F, -25F, -27F);
    Shape5.setTextureSize(128, 128);
    Shape5.mirror = true;
    setRotation(Shape5, 0F, 0F, 0F);
    Shape6 = new ModelRenderer(this, 0, 50);
    Shape6.addBox(0F, 0F, 0F, 7, 9, 9);
    Shape6.setRotationPoint(-3F, -25F, -36F);
    Shape6.setTextureSize(128, 128);
    Shape6.mirror = true;
    setRotation(Shape6, 0F, 0F, 0F);
    jaw = new ModelRenderer(this, 0, 86);
    jaw.addBox(-5F, 0F, -10F, 7, 1, 13);
    jaw.setRotationPoint(2F, -15F, -24F);
    jaw.setTextureSize(128, 128);
    jaw.mirror = true;
    setRotation(jaw, 0.5201081F, 0F, 0F);
    leftleg = new ModelRenderer(this, 0, 0);
    leftleg.addBox(-1F, 0F, 0F, 3, 16, 10);
    leftleg.setRotationPoint(6F, -10F, 11F);
    leftleg.setTextureSize(128, 128);
    leftleg.mirror = true;
    setRotation(leftleg, -0.1745329F, 0F, 0F);
    leftleg2 = new ModelRenderer(this, 0, 106);
    leftleg2.addBox(-1F, 12F, -8F, 3, 15, 5);
    leftleg2.setRotationPoint(6F, -10F, 11F);
    leftleg2.setTextureSize(128, 128);
    leftleg2.mirror = true;
    setRotation(leftleg2, 0.5061455F, 0F, 0F);
    leftleg3 = new ModelRenderer(this, 112, 89);
    leftleg3.addBox(-1F, 19F, 16F, 3, 9, 3);
    leftleg3.setRotationPoint(6F, -10F, 11F);
    leftleg3.setTextureSize(128, 128);
    leftleg3.mirror = true;
    setRotation(leftleg3, -0.4014257F, 0F, 0F);
    Shape11 = new ModelRenderer(this, 0, 72);
    Shape11.addBox(0F, 0F, 0F, 2, 10, 2);
    Shape11.setRotationPoint(5F, -5F, -3F);
    Shape11.setTextureSize(128, 128);
    Shape11.mirror = true;
    setRotation(Shape11, -0.5235988F, 0F, 0F);
    rightleg = new ModelRenderer(this, 54, 51);
    rightleg.addBox(0F, 0F, 0F, 3, 16, 10);
    rightleg.setRotationPoint(-7F, -10F, 11F);
    rightleg.setTextureSize(128, 128);
    rightleg.mirror = true;
    setRotation(rightleg, -0.1745329F, 0F, 0F);
    rightleg2 = new ModelRenderer(this, 23, 106);
    rightleg2.addBox(0F, 12F, -8F, 3, 15, 5);
    rightleg2.setRotationPoint(-7F, -10F, 11F);
    rightleg2.setTextureSize(128, 128);
    rightleg2.mirror = true;
    setRotation(rightleg2, 0.5061455F, 0F, 0F);
    rightleg3 = new ModelRenderer(this, 70, 90);
    rightleg3.addBox(0F, 19F, 16F, 3, 9, 3);
    rightleg3.setRotationPoint(-7F, -10F, 11F);
    rightleg3.setTextureSize(128, 128);
    rightleg3.mirror = true;
    setRotation(rightleg3, -0.4014257F, 0F, 0F);
    leftleg4 = new ModelRenderer(this, 42, 113);
    leftleg4.addBox(-1F, 31F, -1F, 3, 3, 8);
    leftleg4.setRotationPoint(6F, -10F, 11F);
    leftleg4.setTextureSize(128, 128);
    leftleg4.mirror = true;
    setRotation(leftleg4, 0F, 0F, 0F);
    rightleg4 = new ModelRenderer(this, 44, 93);
    rightleg4.addBox(0F, 31F, -1F, 3, 3, 8);
    rightleg4.setRotationPoint(-7F, -10F, 11F);
    rightleg4.setTextureSize(128, 128);
    rightleg4.mirror = true;
    setRotation(rightleg4, 0F, 0F, 0F);
    Shape17 = new ModelRenderer(this, 112, 60);
    Shape17.addBox(-2F, 0F, 0F, 2, 10, 2);
    Shape17.setRotationPoint(-4F, -3.533333F, -3F);
    Shape17.setTextureSize(128, 128);
    Shape17.mirror = true;
    setRotation(Shape17, -0.5235988F, 0F, 0F);
  }
  
  //f1 appears to be a scale of xyz motion.
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    Alosaurus e = (Alosaurus)entity;
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    float newangle = 0;
    //System.out.printf("floats: %f,  %f, %f, %f, %f, %f\n", f, f1, f2, f3, f4, f5);
    if(f1 > 0.1){
    	newangle = MathHelper.cos(f2 * 1.3F * this.wingspeed ) * (float)Math.PI * 0.25F * f1;
    }else{
    	newangle = 0.0F;
    }
    
    this.rightleg.rotateAngleX =  -0.174F + newangle;
    this.rightleg2.rotateAngleX =  0.506F + newangle;
    this.rightleg3.rotateAngleX =  -0.401F + newangle;
    this.rightleg4.rotateAngleX =  newangle;

    this.leftleg.rotateAngleX = -0.174F - newangle;
    this.leftleg2.rotateAngleX = 0.506F - newangle;
    this.leftleg3.rotateAngleX = -0.401F - newangle;
    this.leftleg4.rotateAngleX = -newangle;

    if(e.getAttacking() != 0){
    	this.jaw.rotateAngleX = 0.520F + (MathHelper.cos(f2 * 0.45F ) * (float)Math.PI * 0.18F);
    }else{
    	this.jaw.rotateAngleX = 0.1F;
    }
    
    this.Shape17.rotateAngleX = -0.523F + (MathHelper.cos(f2 * 0.1F ) * (float)Math.PI * 0.05F);
    this.Shape11.rotateAngleX = -0.523F + (MathHelper.cos(f2 * 0.1F ) * (float)Math.PI * 0.05F);

    Shape18.render(f5);
    Shape19.render(f5);
    Shape20.render(f5);
    Shape21.render(f5);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape6.render(f5);
    jaw.render(f5);
    leftleg.render(f5);
    leftleg2.render(f5);
    leftleg3.render(f5);
    Shape11.render(f5);
    rightleg.render(f5);
    rightleg2.render(f5);
    rightleg3.render(f5);
    leftleg4.render(f5);
    rightleg4.render(f5);
    Shape17.render(f5);
    
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

