package Models;

public class Cadastro {

    public int id;
    public double yourhome_lat;
    public double yourhome_long;
    public double yourjob_lat;
    public double yourjob_long;
    public double distancia;

      public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getYourhome_lat() {
        return yourhome_lat;
    }

    public void setYourhome_lat(double yourhome_lat) {
        this.yourhome_lat = yourhome_lat;
    }

    public double getYourhome_long() {
        return yourhome_long;
    }

    public void setYourhome_long(double yourhome_long) {
        this.yourhome_long = yourhome_long;
    }

    public double getYourjob_lat() {
        return yourjob_lat;
    }

    public void setYourjob_lat(double yourjob_lat) {
        this.yourjob_lat = yourjob_lat;
    }

    public double getYourjob_long() {
        return yourjob_long;
    }

    public void setYourjob_long(double yourjob_long) {
        this.yourjob_long = yourjob_long;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
