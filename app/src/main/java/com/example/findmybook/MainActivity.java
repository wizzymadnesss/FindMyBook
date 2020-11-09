package com.example.findmybook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText inputBook;
    private TextView bookTitle;
    private TextView bookAuthor;
    private ImageView bookImage;
    List<Books> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputBook = (EditText)findViewById(R.id.inputbook);
        bookTitle = (TextView)findViewById(R.id.booktitle);
        bookAuthor=(TextView)findViewById(R.id.bookAuthor);
        bookImage = (ImageView)findViewById(R.id.bookImage);

        init();
    }

    public void init(){
    }

    public void searchBook(View view) {
        String searchString = inputBook.getText().toString();

        new GetBook(bookTitle,bookAuthor,bookImage).execute(searchString);
    }

    public class GetBook extends AsyncTask<String,Void,String> {

        private WeakReference<TextView> mTextTitle;
        private WeakReference<TextView> mTextAuthor;
        private WeakReference<ImageView> mImageBook;

        public GetBook(TextView mTextTitle, TextView mTextAuthor,ImageView mImageBook) {
            this.mTextTitle = new WeakReference<>(mTextTitle);
            this.mTextAuthor = new WeakReference<>(mTextAuthor);
            this.mImageBook = new WeakReference<>(mImageBook);
        }

        @Override
        protected String doInBackground(String... strings) {
            return NetUtilities.getBookInfo(strings[0]);
        }

        @Override
        protected void onPostExecute(String s){
            books=new ArrayList<>();
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                int i = 0;
                String title = null;
                String author = null;
                String imagen = null;
                while (i < itemsArray.length() && (title == null && author == null)){
                    JSONObject book = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    JSONObject imageInfo = volumeInfo.getJSONObject("imageLinks");
                    try {
                        title = volumeInfo.getString("title");
                        author = volumeInfo.getString("authors");
                        imagen = imageInfo.getString("thumbnail");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    i++;
                    if (title !=null && author != null){
                        mTextTitle.get().setText(title);
                        mTextAuthor.get().setText(author);
                        Toast.makeText(getApplicationContext(), imagen,Toast.LENGTH_SHORT).show();
                        Glide.with(MainActivity.this).load("http://books.google.com/books/content?id=UnXh1xJRqosC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api").into(bookImage);
                        Log.d(imagen,imagen);
                        books.add(new Books(title,author,imagen));
                    }else {
                        mTextTitle.get().setText("No existen resultados para la consulta");
                        mTextAuthor.get().setText("");
                    }
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

}