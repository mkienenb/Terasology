/*
 * The copyright for these files are held by Richard aka OreSpawn aka TheyCallMeDanger
 */
package org.terasology.rendering.techne.example;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelAnt extends ModelBase
{
  //fields
    ModelRenderer thorax;
    ModelRenderer thorax1;
    ModelRenderer thorax3;
    ModelRenderer abdomen;
    ModelRenderer abdomen1;
    ModelRenderer head;
    ModelRenderer jawsr;
    ModelRenderer jawsl;
    ModelRenderer llegtop1;
    ModelRenderer llegbot1;
    ModelRenderer llegtop2;
    ModelRenderer llegbot2;
    ModelRenderer llegtop3;
    ModelRenderer llegbot3;
    ModelRenderer rlegtop1;
    ModelRenderer rlegbot1;
    ModelRenderer rlegtop2;
    ModelRenderer rlegbot2;
    ModelRenderer rlegtop3;
    ModelRenderer rlegbot3;
  
  public ModelAnt()
  {
    textureWidth = 64;
    textureHeight = 32;
    thorax = new ModelRenderer(this, 22, 0);
    thorax.addBox(0F, 0F, 0F, 3, 3, 3);
    thorax.setRotationPoint(0F, 17F, 0F);
    thorax.setTextureSize(64, 32);
    thorax.mirror = true;
    setRotation(thorax, 0F, 0F, 0F);
    thorax1 = new ModelRenderer(this, 18, 0);
    thorax1.addBox(1F, 1F, -1F, 1, 1, 1);
    thorax1.setRotationPoint(0F, 17F, 0F);
    thorax1.setTextureSize(64, 32);
    thorax1.mirror = true;
    setRotation(thorax1, 0F, 0F, 0F);
    thorax3 = new ModelRenderer(this, 34, 0);
    thorax3.addBox(1F, 1F, 3F, 1, 1, 1);
    thorax3.setRotationPoint(0F, 17F, 0F);
    thorax3.setTextureSize(64, 32);
    thorax3.mirror = true;
    setRotation(thorax3, 0F, 0F, 0F);
    abdomen = new ModelRenderer(this, 38, 0);
    abdomen.addBox(0F, 0F, 4F, 3, 3, 5);
    abdomen.setRotationPoint(0F, 17F, 0F);
    abdomen.setTextureSize(64, 32);
    abdomen.mirror = true;
    setRotation(abdomen, 0F, 0F, 0F);
    abdomen1 = new ModelRenderer(this, 54, 0);
    abdomen1.addBox(1F, 1F, 9F, 1, 1, 1);
    abdomen1.setRotationPoint(0F, 17F, 0F);
    abdomen1.setTextureSize(64, 32);
    abdomen1.mirror = true;
    setRotation(abdomen1, 0F, 0F, 0F);
    head = new ModelRenderer(this, 6, 0);
    head.addBox(0F, -1F, -4F, 3, 3, 3);
    head.setRotationPoint(0F, 17F, 0F);
    head.setTextureSize(64, 32);
    head.mirror = true;
    setRotation(head, 0F, 0F, 0F);
    jawsr = new ModelRenderer(this, 0, 9);
    jawsr.addBox(-1F, 0F, -6F, 1, 1, 3);
    jawsr.setRotationPoint(0F, 17F, 0F);
    jawsr.setTextureSize(64, 32);
    jawsr.mirror = true;
    setRotation(jawsr, 0F, 0F, 0F);
    jawsl = new ModelRenderer(this, 0, 14);
    jawsl.addBox(3F, 0F, -6F, 1, 1, 3);
    jawsl.setRotationPoint(0F, 17F, 0F);
    jawsl.setTextureSize(64, 32);
    jawsl.mirror = true;
    setRotation(jawsl, 0F, 0F, 0F);
    llegtop1 = new ModelRenderer(this, 15, 10);
    llegtop1.addBox(3F, 1F, 1F, 3, 1, 1);
    llegtop1.setRotationPoint(0F, 17F, 0F);
    llegtop1.setTextureSize(64, 32);
    llegtop1.mirror = true;
    setRotation(llegtop1, 0F, 0F, 0.3839724F);
    llegbot1 = new ModelRenderer(this, 15, 19);
    llegbot1.addBox(5F, -3F, 1F, 3, 1, 1);
    llegbot1.setRotationPoint(0F, 17F, 0F);
    llegbot1.setTextureSize(64, 32);
    llegbot1.mirror = true;
    setRotation(llegbot1, 0F, 0F, 1.064651F);
    llegtop2 = new ModelRenderer(this, 15, 13);
    llegtop2.addBox(3F, 1F, 2F, 3, 1, 1);
    llegtop2.setRotationPoint(0F, 17F, 0F);
    llegtop2.setTextureSize(64, 32);
    llegtop2.mirror = true;
    setRotation(llegtop2, 0F, -0.2094395F, 0.3839724F);
    llegbot2 = new ModelRenderer(this, 15, 22);
    llegbot2.addBox(5F, -3F, 2F, 3, 1, 1);
    llegbot2.setRotationPoint(0F, 17F, 0F);
    llegbot2.setTextureSize(64, 32);
    llegbot2.mirror = true;
    setRotation(llegbot2, 0F, -0.2268928F, 1.064651F);
    llegtop3 = new ModelRenderer(this, 15, 16);
    llegtop3.addBox(3F, 1F, 0F, 3, 1, 1);
    llegtop3.setRotationPoint(0F, 17F, 0F);
    llegtop3.setTextureSize(64, 32);
    llegtop3.mirror = true;
    setRotation(llegtop3, 0F, 0.3490659F, 0.3839724F);
    llegbot3 = new ModelRenderer(this, 15, 25);
    llegbot3.addBox(5F, -3F, 0F, 3, 1, 1);
    llegbot3.setRotationPoint(0F, 17F, 0F);
    llegbot3.setTextureSize(64, 32);
    llegbot3.mirror = true;
    setRotation(llegbot3, 0F, 0.3490659F, 1.064651F);
    rlegtop1 = new ModelRenderer(this, 25, 10);
    rlegtop1.addBox(-4F, 2F, 1F, 3, 1, 1);
    rlegtop1.setRotationPoint(0F, 17F, 0F);
    rlegtop1.setTextureSize(64, 32);
    rlegtop1.mirror = true;
    setRotation(rlegtop1, 0F, 0F, -0.4712389F);
    rlegbot1 = new ModelRenderer(this, 25, 19);
    rlegbot1.addBox(-7F, 0F, 1F, 3, 1, 1);
    rlegbot1.setRotationPoint(0F, 17F, 0F);
    rlegbot1.setTextureSize(64, 32);
    rlegbot1.mirror = true;
    setRotation(rlegbot1, 0F, 0F, -0.9773844F);
    rlegtop2 = new ModelRenderer(this, 25, 13);
    rlegtop2.addBox(-4F, 2F, 0F, 3, 1, 1);
    rlegtop2.setRotationPoint(0F, 17F, 0F);
    rlegtop2.setTextureSize(64, 32);
    rlegtop2.mirror = true;
    setRotation(rlegtop2, 0F, -0.5934119F, -0.4712389F);
    rlegbot2 = new ModelRenderer(this, 25, 22);
    rlegbot2.addBox(-7F, 0F, 0F, 3, 1, 1);
    rlegbot2.setRotationPoint(0F, 17F, 0F);
    rlegbot2.setTextureSize(64, 32);
    rlegbot2.mirror = true;
    setRotation(rlegbot2, 0F, -0.5934119F, -0.9773844F);
    rlegtop3 = new ModelRenderer(this, 25, 16);
    rlegtop3.addBox(-4F, 2F, 2F, 3, 1, 1);
    rlegtop3.setRotationPoint(0F, 17F, 0F);
    rlegtop3.setTextureSize(64, 32);
    rlegtop3.mirror = true;
    setRotation(rlegtop3, 0F, 0.418879F, -0.4712389F);
    rlegbot3 = new ModelRenderer(this, 25, 25);
    rlegbot3.addBox(-7F, 0F, 2F, 3, 1, 1);
    rlegbot3.setRotationPoint(0F, 17F, 0F);
    rlegbot3.setTextureSize(64, 32);
    rlegbot3.mirror = true;
    setRotation(rlegbot3, 0F, 0.418879F, -0.9773844F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);  
    
    //Yes, it's supposed to be rotateAngleY, but this still works and looks pretty cool
    this.llegtop1.rotateAngleX = MathHelper.cos(f2 * 2.7F) * (float)Math.PI * 0.45F * f1;
    this.llegbot1.rotateAngleX = this.llegtop1.rotateAngleX;
    this.rlegtop2.rotateAngleX = this.llegtop1.rotateAngleX;
    this.rlegbot2.rotateAngleX = this.llegtop1.rotateAngleX;
    this.rlegtop3.rotateAngleX = this.llegtop1.rotateAngleX;
    this.rlegbot3.rotateAngleX = this.llegtop1.rotateAngleX;
    
    this.rlegtop1.rotateAngleX = -this.llegtop1.rotateAngleX;
    this.rlegbot1.rotateAngleX = -this.llegtop1.rotateAngleX;
    this.llegtop2.rotateAngleX = -this.llegtop1.rotateAngleX;
    this.llegbot2.rotateAngleX = -this.llegtop1.rotateAngleX;
    this.llegtop3.rotateAngleX = -this.llegtop1.rotateAngleX;
    this.llegbot3.rotateAngleX = -this.llegtop1.rotateAngleX;
    
    this.jawsl.rotateAngleY = MathHelper.cos(f2 * 0.4F) * (float)Math.PI * 0.05F;
    this.jawsr.rotateAngleY = -this.jawsl.rotateAngleY;
    
    thorax.render(f5);
    thorax1.render(f5);
    thorax3.render(f5);
    abdomen.render(f5);
    abdomen1.render(f5);
    head.render(f5);
    jawsr.render(f5);
    jawsl.render(f5);
    llegtop1.render(f5);
    llegbot1.render(f5);
    llegtop2.render(f5);
    llegbot2.render(f5);
    llegtop3.render(f5);
    llegbot3.render(f5);
    rlegtop1.render(f5);
    rlegbot1.render(f5);
    rlegtop2.render(f5);
    rlegbot2.render(f5);
    rlegtop3.render(f5);
    rlegbot3.render(f5);   
  }
  
  private void setRotation(ModelRenderer model, float rotationAngleX, float rotationAngleY, float rotationAngleZ)
  {
    model.rotateAngleX = rotationAngleX;
    model.rotateAngleY = rotationAngleY;
    model.rotateAngleZ = rotationAngleZ;
  }
  
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
  {
    super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
  }
  

}


