package poly.bedtech;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
 
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
 
public class StructureAPI {
 
    static Plugin plugin;
 
    /**
    * Get all blocks between two points and return a 3d array
    */
 
    public static Material[][][] getStructureByLocation(Location l1, Location l2){
    	
    	return getStructure(l1.getBlock(),l2.getBlock());
    	
    }
    
    
    public static Material[][][] getStructure(Block block, Block block2){
        int minX, minZ, minY;
        int maxX, maxZ, maxY;
 
 
        //Couldv'e used Math.min()/Math.max(), but that didn't occur to me until after I finished this :D
        minX = block.getX() < block2.getX() ? block.getX() : block2.getX();
        minZ = block.getZ() < block2.getZ() ? block.getZ() : block2.getZ();
        minY = block.getY() < block2.getY() ? block.getY() : block2.getY();
 
        maxX = block.getX() > block2.getX() ? block.getX() : block2.getX();
        maxZ = block.getZ() > block2.getZ() ? block.getZ() : block2.getZ();
        maxY = block.getY() > block2.getY() ? block.getY() : block2.getY();
 
        Material[][][] blocks = new Material[maxX-minX+1][maxY-minY+1][maxZ-minZ+1];
 
        for(int x = minX; x <= maxX; x++){
 
            for(int y = minY; y <= maxY; y++){
 
                for(int z = minZ; z <= maxZ; z++){
 
                    Block b = block.getWorld().getBlockAt(x, y, z);
                    blocks[x-minX][y-minY][z-minZ] = b.getType();
                }
            }
        }
 
        return blocks;
 
    }
 
 
    /**
    * Pastes a structure to a desired location
    */
 
    public static void paste(Material[][][] blocks, Location l){
        for(int x = 0; x < blocks.length; x++){
 
            for(int y = 0; y < blocks[x].length; y++){
 
                for(int z = 0; z < blocks[x][y].length; z++){
                    Location neww = l.clone();
                    neww.add(x, y, z);
                    Block b = neww.getBlock();
                    if(blocks[x][y][z] != null){
                    	b.setType(blocks[x][y][z]);
                        b.getState().update(true);
                    }
                }
 
            }
        }
    }
 
    /**
    * Save a structure with a desired name
    */
 
    public static void save(String name, Material[][][] b){
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
 
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + ".schem");
        File dir = new File(plugin.getDataFolder() + "/schematics");
 
        try {
                dir.mkdirs();
                f.createNewFile();
        } catch (IOException e1) {
                e1.printStackTrace();
            }
 
        try{
               fout = new FileOutputStream(f);
               oos = new ObjectOutputStream(fout);
               oos.writeObject(b);
        } catch (Exception e) {
               e.printStackTrace();
        }finally {
               if(oos  != null){
                   try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
 
    /**
    * Load structure from file
    */
 
    public static Material[][][] load(String name){
 
        File f = new File(plugin.getDataFolder() + "/schematics/"+ name + ".schem");
 
        Material[][][] loaded = new Material[0][0][0];
 
        try {
            FileInputStream streamIn = new FileInputStream(f);
           ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
           loaded = (Material[][][])objectinputstream.readObject();
 
           objectinputstream.close();
 
       } catch (Exception e) {
 
           e.printStackTrace();
    }
 
        return loaded;
    }
 
    /**
    * Some methods I used to test
    *
    */
 
    public void printArray(int[][][] a){
        for(int i = 0; i < a.length; i++){
            System.out.println(toString(a[i]));
        }
    }
 
    public String toString(int[][] a){
        String s = "";
        for (int row=0; row < a.length ; ++row) {
          s += Arrays.toString(a[row]) + "\n";
        }
        return s;
    }
 
 
 
}
