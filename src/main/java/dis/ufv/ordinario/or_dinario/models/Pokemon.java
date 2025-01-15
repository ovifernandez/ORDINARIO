package dis.ufv.ordinario.or_dinario.models;

public class Pokemon {
    private int numero;
    private String name, tipo1, tipo2;
    private int total, hp, attack, defense, speedAttack, speedDefense, speed, generation;
    private boolean legendary;

    public Pokemon(String [] fila){
        this.numero = Integer.parseInt(fila[0]);
        this.name = fila[1];
        this.tipo1 = fila[2];
        this.tipo2 = fila[3];
        this.total = Integer.parseInt(fila[4]);
        this.hp = Integer.parseInt(fila[5]);
        this.attack = Integer.parseInt(fila[6]);
        this.defense = Integer.parseInt(fila[7]);
        this.speedAttack = Integer.parseInt(fila[8]);
        this.speedDefense = Integer.parseInt(fila[9]);
        this.speed = Integer.parseInt(fila[10]);
        this.generation = Integer.parseInt(fila[11]);
        this.legendary = Boolean.parseBoolean(fila[12]);
    }
}
