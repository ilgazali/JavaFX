package application;
	
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class Main extends Application {
	int index = 0;
	int ID = 1;
	Person[] abArray;
	// Specify the size of five string fields in the record
	final static int ID_SIZE = 4;
	final static int NAME_SIZE = 32;
	final static int STREET_SIZE = 32;
	final static int CITY_SIZE = 20;
	final static int GENDER_SIZE = 1;
	final static int ZIP_SIZE = 5;
	final static int RECORD_SIZE = (NAME_SIZE+STREET_SIZE+CITY_SIZE+GENDER_SIZE+ZIP_SIZE);
	public RandomAccessFile raf;
	
    // Text fields
	TextField tfID = new TextField();
	TextField tf_SEARCH_UPDATE_ID = new TextField();
	TextField tfNAME = new TextField();
	TextField tfSTREET = new TextField();
	TextField tfCITY = new TextField();
	TextField tfGENDER = new TextField();
	TextField tfZIP = new TextField();
	
	// Buttons
	
	Button btAdd = new Button("Add");
	Button btFirst = new Button("First");
	Button btNext = new Button("Next");
	Button btPrevious = new Button("Previous");
	Button btLast = new Button("Last");
	Button btUpdateByID = new Button("UpdateByID");
	Button btSearchByID = new Button("SearchByID");
	Button btClean = new Button("Clean textFields");
	
	// Labels
	
	Label lbID = new Label("ID");
	Label lb_SEARCH_UPDATE_ID = new Label("  Search/Update ID");
	Label lbNAME = new Label("Name");
	Label lbSTREET = new Label("Street");
	Label lbCITY = new Label("City");
	Label lbGENDER = new Label("Gender");
	Label lbZIP = new Label("Zip");
	
    

	
	public Main() {
		
		try{
			
			raf = new RandomAccessFile("AddressBook.dat","rw");
			abArray = new Person[100];
			
		}catch (Exception e) {
			
			e.printStackTrace();
			System.exit(1);
		}
	}
	

    
	
	@Override
	public void start(Stage primaryStage) {
		try {
		
		    tfID.setPrefColumnCount(4);
			tfID.setDisable(true);
		    tf_SEARCH_UPDATE_ID.setPrefColumnCount(12);
		    tfNAME.setPrefColumnCount(4);
		    tfSTREET.setPrefColumnCount(4);
		    tfCITY.setPrefColumnCount(12);
		    tfGENDER.setPrefColumnCount(1);
		    tfZIP.setPrefColumnCount(4);
		    
		    Alert alert = new Alert(AlertType.INFORMATION);
		    alert.setTitle("Information Dialog");
		    alert.setHeaderText("Look, an Information Dialog");
	
		    // User interface design
		    GridPane p = new GridPane();
		    p.setAlignment(Pos.CENTER);
		    p.setHgap(10);
		    p.setVgap(10);
		    
		    p.add(lbID, 0, 0);
		    
		    HBox p1 = new HBox(10);
		    p1.getChildren().addAll(tfID,lb_SEARCH_UPDATE_ID,tf_SEARCH_UPDATE_ID);
		    p.add(p1, 1,0);
	        
		    
		    p.add(lbNAME,0,1);
		    p.add(tfNAME,1,1 );
		    
		    p.add(lbSTREET,0,2);
		    p.add(tfSTREET,1,2);
		    
		    p.add(lbCITY,0,3);
		    
		    HBox p2 = new HBox(10);
		    p2.getChildren().addAll(tfCITY,lbGENDER,tfGENDER,lbZIP,tfZIP);
		    p.add(p2,1,3);
		    // for buttons
		    HBox p3 = new HBox(10);
		    p3.getChildren().addAll(btAdd,btFirst,btNext,btPrevious,btLast,btUpdateByID,btSearchByID,btClean);
		    p3.setAlignment(Pos.CENTER);
		    // border pane
		    BorderPane borderPane  = new BorderPane();
		    borderPane.setCenter(p);
		    borderPane.setBottom(p3);
		    
		    Scene scene = new Scene(borderPane,600,200);
		    primaryStage.setTitle("Address Book");
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    
		    // Display the first record if available
		    try {
				
		    	if(raf.length()>0) {
					
					long currentPos = raf.getFilePointer();
					
					while(currentPos < raf.length()) 
					{
						readFileFillArray(abArray,currentPos);
						currentPos=raf.getFilePointer();
					}
					readFilebyPos(0);
				}
		    	
		    	
			} catch (IOException e){
				e.printStackTrace();
			}
		    
		    // "Add" button
		    btAdd.setOnAction(e->{
		    	
		 try {
			 
			    writeAddressToFile(raf.length());
			    readFileFillArray(abArray, RECORD_SIZE*2*(index));
			    alert.setHeaderText("Look, an Information Dialog");
			    alert.setContentText("Record is added successfully!");
 			    alert.showAndWait();
			    
			    cleanTextFields();
			    
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		    		
		    	
		    });
		   
		    // "First" button
		    btFirst.setOnAction(e->{
		    	if(index > 0)
		    	{
		    		traverseArray(abArray, 0);
		    	}
		    	
		    	  	
		    });
		    
		    // "Next" button
            btNext.setOnAction(e->{
           	try {
            		
            			int id=Integer.parseInt(tfID.getText().trim());
    					traverseArray(abArray, id);
    		    
    		    					
				}
				catch(Exception ex) {
				JOptionPane.showMessageDialog(null,"You reached the end of the list.");
			}
		    });
            
            // "Previous" button
            btPrevious.setOnAction(e->{
            	try {
					int id=Integer.parseInt(tfID.getText());
					traverseArray(abArray, id-2);
				}
				catch(Exception ex) {
				JOptionPane.showMessageDialog(null,"You are already in the first element of the list.");
				}
            });
            
            // "Last" button
		    btLast.setOnAction(e ->{
		    	
					if (index <= abArray.length)
					{
						traverseArray(abArray, index-1);
					}
		    });
		    
		    // "UpdateByID" button
		    btUpdateByID.setOnAction(e->{
				try {
			
					int id=Integer.parseInt(tf_SEARCH_UPDATE_ID.getText());
					abArray[id-1].setId(id);
					abArray[id-1].setName(tfNAME.getText());														
					abArray[id-1].setStreet(tfSTREET.getText());									  				
					abArray[id-1].setCity(tfCITY.getText());
					abArray[id-1].setGender(tfGENDER.getText());
					abArray[id-1].setZip(tfZIP.getText());				    										
					try {
						
					Uptade_Person(id-1,tfNAME.getText(),tfSTREET.getText(),tfCITY.getText(),tfGENDER.getText(),tfZIP.getText());
					JOptionPane.showMessageDialog(null, "Uptade is Succesful!");
					
					}
					
					catch(Exception ex) {
						
					}
					}
					catch(Exception ex1) {
						JOptionPane.showMessageDialog(null, "Uptade is not Succesful!\n\nYou cannot update without entering an ID number.\n"
								+ "Make sure you are not entering an invalid ID.");
						
					}
		    	
		    });
		    
		    // "SearchByID" button
		    btSearchByID.setOnAction(e->{
		    	try {
					int id=Integer.parseInt(tf_SEARCH_UPDATE_ID.getText());
					traverseArray(abArray, id-1);
					
					}catch (Exception ex) {
						    alert.setHeaderText("No Records Found!");
						    alert.setContentText("There is no record in the id you are looking for.\nPlease make sure to enter a valid ID.");
			 			    alert.showAndWait();
					}
		    });
		    
		    // "Clean textFields" button
		    btClean.setOnAction(e ->{
		    	try {
					cleanTextFields();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		    	
		    });
		    
		    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//write a record at the end of the file
	public void writeAddressToFile(long position) {
		try {
			
		raf.seek(position);
		FileOperations.writeFixedLengthString(tfNAME.getText(), NAME_SIZE, raf);
		FileOperations.writeFixedLengthString(tfSTREET.getText(), STREET_SIZE, raf);
		FileOperations.writeFixedLengthString(tfCITY.getText(),CITY_SIZE, raf);
		FileOperations.writeFixedLengthString(tfGENDER.getText(), GENDER_SIZE, raf);
		FileOperations.writeFixedLengthString(tfZIP.getText(), ZIP_SIZE, raf);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void cleanTextFields() 
	{
		
		tf_SEARCH_UPDATE_ID.clear();
		tfID.clear();
		tfNAME.clear();
		tfSTREET.clear();
		tfCITY.clear();
		tfGENDER.clear();
		tfZIP.clear();
		
	}
	
	
	public void readFileFillArray(Person[] people,long position) throws IOException {
		raf.seek(position);
		
		int ID=index+1;
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf).trim();
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf).trim();
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf).trim();
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf).trim();
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf).trim();
		
	Person person = new Person(ID,name,street,city,gender,zip);
	people[index] = person; 
	index++;		
	
	}
	
	public void readFilebyPos(long position) throws IOException {
		raf.seek(position);
		
		String name = FileOperations.readFixedLengthString(NAME_SIZE, raf);
		String street = FileOperations.readFixedLengthString(STREET_SIZE, raf);
		String city = FileOperations.readFixedLengthString(CITY_SIZE, raf);
		String gender = FileOperations.readFixedLengthString(GENDER_SIZE, raf);
		String zip = FileOperations.readFixedLengthString(ZIP_SIZE, raf);
		
		
		tfID.setText(String.valueOf(ID));
		tfNAME.setText(name);
		tfSTREET.setText(street);
		tfCITY.setText(city);
		tfGENDER.setText(gender);
		tfZIP.setText(zip);
		
	}

	public  void traverseArray(Person[]people, int index) {
		
		tfID.setText(String.valueOf(abArray[index].getId()));
		tfNAME.setText(abArray[index].getName());
		tfSTREET.setText(abArray[index].getStreet());
		tfCITY.setText(abArray[index].getCity());
		tfGENDER.setText(abArray[index].getGender());
		tfZIP.setText(abArray[index].getZip());
		
	}
	public void Uptade_Person(int id,String name,String street, String city,String gender,String zip) throws IOException {
		
		abArray[id].setName(name);
		abArray[id].setStreet(street);
		abArray[id].setCity(city);
		abArray[id].setGender(gender);
		abArray[id].setZip(zip);	
		long position= RECORD_SIZE*2*(id);
		writeAddressToFile(position);
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}