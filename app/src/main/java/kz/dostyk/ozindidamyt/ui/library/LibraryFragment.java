package kz.dostyk.ozindidamyt.ui.library;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import kz.dostyk.ozindidamyt.R;
import kz.dostyk.ozindidamyt.ui.cinema.models.Cinema;
import kz.dostyk.ozindidamyt.ui.library.activities.OneBookAcvitiy;
import kz.dostyk.ozindidamyt.ui.library.adapters.BookListAdapter;
import kz.dostyk.ozindidamyt.ui.library.models.Book;

public class LibraryFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private RecyclerView bookListRv;
    private BookListAdapter bookListAdapter;
    private ArrayList<Book> bookList;
    private ArrayList<Book> bookListCopy, bookListCopy2;
    private RecyclerView.LayoutManager linearLayoutManager, gridLayoutManager;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;
    View sortDialogView;
    AlertDialog filterDialog;
    ImageView monthBookPhoto;
    TextView monthBName, monthAuthor;
    DatabaseReference mDatabaseRef;
    Book monthBook;
    RelativeLayout monthBookLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_library_new, container, false);
        initViews();
//        initializeSearchView();

        return rootView;
    }

    public void initViews() {
        bookListRv = rootView.findViewById(R.id.bookListRv);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("monthBook");

        monthBookLayout = rootView.findViewById(R.id.monthBookLayout);
        monthBookPhoto = rootView.findViewById(R.id.monthBookPhoto);
        monthBName = rootView.findViewById(R.id.monthBName);
        monthAuthor = rootView.findViewById(R.id.monthAuthor);
        initMonthBook();

        monthBookPhoto.setOnClickListener(this);
        monthBName.setOnClickListener(this);
        monthAuthor.setOnClickListener(this);

        bookList = new ArrayList<>();
        bookListCopy = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        fillBooks();
        bookListCopy.addAll(bookList);

        bookListAdapter = new BookListAdapter(getActivity(), bookList);

        int resId = R.anim.layout_anim_book;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);

        bookListRv.setLayoutManager(gridLayoutManager);
        bookListRv.setItemAnimator(new DefaultItemAnimator());
        bookListRv.setLayoutAnimation(animation);
        bookListRv.setAdapter(bookListAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
    }

    public void initMonthBook(){
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    monthBook = dataSnapshot.getValue(Book.class);

                    Glide.with(getContext())
                            .load(monthBook.getPhoto())
                            .placeholder(R.drawable.ic_book)
                            .into(monthBookPhoto);

                    monthBName.setText(monthBook.getName());
                    monthAuthor.setText(monthBook.getAuthor());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void filterDialog(){
        LayoutInflater factory = LayoutInflater.from(getActivity());

        sortDialogView  = factory.inflate(R.layout.dialog_filter_language, null);
        filterDialog = new AlertDialog.Builder(getActivity()).create();

        LinearLayout bookKaz   = sortDialogView.findViewById(R.id.bookKaz);
        LinearLayout bookRus = sortDialogView.findViewById(R.id.bookRus);

        TextView tv_kaz = sortDialogView.findViewById(R.id.tv_kaz);
        TextView tv_rus = sortDialogView.findViewById(R.id.tv_rus);
        tv_kaz.setText("Қазақша кітаптар");
        tv_rus.setText("Книги на русском");

        bookKaz.setOnClickListener(this);
        bookRus.setOnClickListener(this);

        filterDialog.setView(sortDialogView);
        filterDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.monthBookPhoto:
            case R.id.monthBName:
            case R.id.monthAuthor:

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(monthBook.getUrl()));
                startActivity(i);

                break;

            case R.id.bookKaz:

                Predicate<Book> byKz = book -> book.getLanguage().equals("kz");

                ArrayList bookListFilter = (ArrayList<Book>) bookListCopy.stream().filter(byKz).collect(Collectors.toList());
                bookList.clear();
                bookList.addAll(bookListFilter);

                bookListAdapter.notifyDataSetChanged();
                filterDialog.dismiss();
                break;
            case R.id.bookRus:

                Predicate<Book> byRus = book -> book.getLanguage().equals("rus");

                ArrayList bookListFilter2 = (ArrayList<Book>) bookListCopy.stream().filter(byRus).collect(Collectors.toList());
                bookList.clear();
                bookList.addAll(bookListFilter2);

                bookListAdapter.notifyDataSetChanged();
                filterDialog.dismiss();
                break;
        }
        /*
        switch (view.getId()) {
            case R.id.btn_kz_lang:
                btnPressedState(btn_kz);
                normalState(btn_rus);

                Predicate<Book> byKz = book -> book.getLanguage().equals("kz");

                ArrayList bookListFilter = (ArrayList<Book>) bookListCopy.stream().filter(byKz).collect(Collectors.toList());
                bookList.clear();
                bookList.addAll(bookListFilter);

                bookListAdapter.notifyDataSetChanged();
                break;

            case R.id.btn_rus_lang:
                normalState(btn_kz);
                btnPressedState(btn_rus);

                Predicate<Book> byRus = book -> book.getLanguage().equals("rus");

                ArrayList bookListFilter2 = (ArrayList<Book>) bookListCopy.stream().filter(byRus).collect(Collectors.toList());
                bookList.clear();
                bookList.addAll(bookListFilter2);

                bookListAdapter.notifyDataSetChanged();

                break;
        }

         */
    }

    /*
    public void initializeSearchView(){
        searchView = rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
    }
    */
    public void refreshItems(){
        swipeRefreshLayout.setRefreshing(false);
    }

    public void fillBooks(){

        bookList.add(new Book("",
                "Тастамашы ана",
                "Айгиз Баймұхаметов",
                "Тастамашы ана - бұл кітапта башқұрт жазушысы Айгиз Баймұхаметов ата-анасынан айырылған жетім балалардың аянышты тағдырын баяндайды. Оқиға анасы бақиға аттанған балалардың төсек тартып жатқан әкесіне қамқорлығымен бастау алып, таңғы шайға әкесін  оята алмаған Әлияның жан түршігерлік айқайымен жалғасын табады. \"Шіркін менің ата-анам дүниеден өтпегенде ғой, олар түрмеде немесе мені тастап кеткен болғанда ғой!\"-деп армандайтын балалар үйіндегі жетімдердің өмірі, көрген қиыншылығы оқырманға түсінікті тілде суреттеледі. Жазушы бас кейіпкер Ілиястың жан дүниесін ашу арқылы бүгінгі қоғамның бет-бейнесін көрсеткісі келген. Кітапты оқыған балалар ата-ананың қадірін түсініп,бақытты аялауды үйренеді. Үлкендер жас ұрпақтың мейірімге зәру екенін ұғынады. Кітап балалар мен жасөспірімдерге, барша оқырман қауымға арналған.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/21", ""));

        bookList.add(new Book("",
                "Жусан иісі",
                "Сайын Мұратбеков",
                "Жусан иісі- жастайынан әке-шешесінен айырылып, әжесімен бірге бір ауылға көшіп келген, кейіннен әжесі де дүниеден өтіп жетім қалған Аян есімді зерек, қайсар баланың басынан өткерген қиындықтары суреттелген, соғыстың кезінде жарық көрген  повесть. Авторы:Сайын Мұратбеков. Болған оқиға желісімен жазылған бұл шығармаға аяғын қисаңдатып,ішіне қарай басатын Ұзақбай деген баланың тағдыры арқау болған. Жүрегіңе қанжар қадап,көзіңе жас алардай әсер беретін бұл шығарма оқырманға басқа түскен қиындыққа мойымауды үйретеді.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/21", ""));

        bookList.add(new Book("",
                "Күміс кітап",
                "Бақытжан Бұқарбай",
                "«Күміс кітап» – Бақытжан Бұқарбайдың қазіргі қоғамдағы кітап оқу мәселесіне байланысты 2016 жылғы зерттеу нәтижесіне сүйене отырып жазған еңбегі. Еңбек қазақ тіліндегі «№1 жыл бестселлері» деген атқа ие болған. 5 тарау, пікір-сұхбаттардан тұрады. Кітаптың негізгі мақсаты – жастарды кітап оқуға шақыру. Негізгі мақсатына сәйкес, бүгінгі қоғамның кей мәселелеріне қатысты толғаулар мен естеліктерден де тұрады. Қазіргі таңда кітаптың көптігі мен оқырман санының аздығын негізге алып, ақпарат жедел дамыған заманда білім алушылардың аздығына тоқталып өткен. Кітап жас таңдамайды, барлық оқырманға арналған.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/18", ""));

        bookList.add(new Book("",
                "Ақыл қалта",
                "М. Кемел",
                "Ақыл қалта - парасат тамырын жетілдіретін, даналық сөздер жинағы.  Бұл кітапта өмірді жаңадан бастаған жастар үшін де, жасы бел ортасына келіп өмірден өз орнын  таба алмай жүргендерге де зерделеріне тоқырлық көптеген даналық ойлар бар. Жинақта орын алған тұжырымдар мен жазбалар адам санасының дамуына, ой-өрісінің кемелденуіне жол сілтейді. Автор даналар айтқан асыл сөздерді тірілтіп, бүгінгі өмірге лайықтаған ой түйіндері мен толғамдарын жеткізеді. Кітап көп елдердің тілдерінде бірнеше қайтара жарық көрген. Ол әлемдік пәлсапаның інжу-маржандарының бірі. Себебі кітапта өмір сүрудің тәсілдерін зерттеп, кейінгіге мәңгі мұра боларлық ақыл қалдырылған.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/20", ""));

        bookList.add(new Book("",
                "Абайдың қара сөздері",
                "Абай Құнанбаев",
                "Абайдың қара сөздері- автор өзінің көрген-білгенін,танып-түстегенін ақыл-көрігінде таразыға салып ұсынған 45 қара сөзден тұратын жинағы. Кез-келген жастағы оқырманға арналған бұл кітаптың берері ұлан-ғайыр. Ішкі мазмұны адамның дүние есігін ашқанынан бастап, ақыретке кеткенге дейінгі аралықтағы өмір сырын жік-жікке бөліп, жүйелеп түсіндіреді. Онда нəпсі мен қанағаттың, бай мен кедейдің, ақ пен қараның, адал мен арамның көзге көрінбейтін, қолға ұсталмайтын нəзік пернелері рентгенмен түсіргендей аса бір дəлдікпен дөп басып ажыратылып отырады. Әрбір қара сөзі тұнып тұрған даналық пен тәрбиеге толы бұл жинақ сізге адам- адам болып  өмір сүру үшін не істеу керектігін ұғындырады.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/17", ""));

        bookList.add(new Book("",
                "Екінші болма",
                "Қуаныш Шонбай",
                "«Екінші болма» - бизнесте, карьерада және өмірде жеңіске жетудің қазақша жолы. Кітап Қуаныш Шонбайдың шынайы өз өмірінен алынған туындысы, тұлғаны және бизнесті дамытуға қатысты ақпараттармен бөлісетін еңбек. Табысты еңбек ету мен даму жолында жазылған қазақ тілді ортаға арналған таптырмас туынды. Жоспарды дұрыс құруға, мақсатты айқындауға (мақсатты дұрыс қоюға) көмекші болады. Мақсатқа жету жолында өзіңді дамытып, өзіңмен күресіп, өзіңмен жұмыс істеуге, тәжірибе жинауға, толықтай тұлға болып қалыптасуға шақырады. Қателіктер жасаудан қорықпай, батыл шешім қабылдауға үндейді. Адал жолмен, адал кәсіппен, ізденімпаздылықпен бірінші болуға шақырады. Мүмкіндіктерді барынша пайдалану арқылы өз кәсібіңнің (ісіңнің) алғашқысы болуға жетелейтін таптырмас еңбек.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/25", ""));

        bookList.add(new Book("",
                "Түйсігіңмен ойлай біл",
                "Джон Кехо",
                "Түйсігіңмен ойлай біл еңбегінің авторы - канадалық жазушы, адамның жеке дамуына қатысты ұстаздық  еткен Джон Кехо  (John Kehoe). Өзін жетілдіру жолында аянбай еңбек етіп, өз орнын табу арқылы адамдарға көмекші бола білген. Жиырма жыл ішінде осы жүйе бойынша жүз мыңнан астам адамды оқытқан. Жүз мыңнан астам адамға дұрыс ойлау мен дұрыс шешім шығаруды, нәтижені дұрыс қабылдауды, қоршаған ортамен тығыз байланыс жасаудың маңыздылығын түсіндіреді. Өз өмірінде пайдаланған тәсілдер арқылы табиғи күшті пайдалану мен оны басқара алу, өз мақсатына сәйкес жүзеге асырудың жолдарымен таныстырады. Оны күнделікті іске асыруға себепші болады. Әрбір құбылысқа, жаратылысқа жіті мән бергенде ғана адам дұрыс ойланып,  дұрыс түсіне алатынын дәлелдейді. Автордың айтуынша, кітаптың жарық көруіне орай онымен әлемнің миллиондаған адамы  таныс болады, яғни миллиондаған адам түйсікпен шынайы ойлана білуді үйренеді екен.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/24", ""));

        bookList.add(new Book("",
                "26 жылда қалыптасқан 26 қағида",
                "Данияр Жігітбек",
                "«26 жылда қалыптасқан 26 қағида» - Данияр Жігітбектің өз өмірі бойынша жазылған еңбек. Автор 26 жылда көрген-білгені, түйгені мен түсінгенін 26 қағидамен ықшамдап берген. Бұл қағидаларды жазудағы мақсаты: өткен өміріне шолу жасау; ғұмырында із қалдырған сәттерді естелік етіп жазу; аз да болса өмірлік тәжірибесімен бөлісу, өзгелерге септігін тигізу. Қағидалар адамның өз өмірінде болып жатқан өзгерістерге бейімделе алуға, сәтсіздіктерге мойымауға, жақсылық пен жамандықты дұрыс қабылдауға үндейді. Адамның қолында бар нығыметтерге шүкір етіп, барға қанағат, жоққа сабыр сақтауды үйретеді. Шынайы ықыласпен жасалған әрбір өзгерістің сәттілік алып келетінін түсіндіреді. Қағидалармен таныс болған адам, өз өміріндегі жаңа өзгерістерге жол ашады, батыл шешім қабылдауға тәуекел етеді.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/23", ""));

        bookList.add(new Book("",
                "Үш студент",
                "Артур Конан Дойл",
                "Үш студент:- \"Әулие Лука колледжінде\" болған, тұрпайы болғанымен тағылымы мол, оқығанда бірден баурай кететін, жұмбақтың шешімі қалай аяқталар екен деп,-абдыратып қоятын қазақ тіліне аударылған тартымды шағын әңгіме. Айтулы колледждің оқытушысы Хилтон Сомс күні ертең болатын, аса қомақты сома берілетін, өте жоғары дәрежедегі \"Фортескью\" стипендиясының емтихан мәтініне сұғанақтық жасалғанына күмәнданып, небір күрделі істің жұмбағын шешуге талантты Шерлок Холмсқа осы оқиғаның шешімін табуға көмек сұрайды. Өте зерек Шерлок Холмс асқан дарындылықпен бұл іске кімнің қатысы бар екенін бір түнде анықтап береді. Бұл оқиға ұят ақшаға сатылмайтынын, кез-келген істің шешімін сабырлықпен, зейінділікпен шешуге болатынын үйретеді. Шығарма кез-келген жастағы оқырманға арналған.",
                "kz",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/22", ""));

        bookList.add(new Book("",
                "Важные годы",
                "Мэг Джей",
                "Важные годы - эта книга о десятилетии, определяющем судьбу человека. Инвестиции, сделанные в этот период в собственное развитие во всех сферах жизни, принесут максимальную отдачу. Автор объясняет, почему не стоит откладывать начало взрослой жизни на потом, и рассказывает, что нужно делать в это время жизни человека.\n" +
                        "На русском языке публикуется впервые.",
                "rus",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/34", ""));

        bookList.add(new Book("",
                "Маленький Принц",
                "Антуаном де Сент-Экзюпери",
                "Маленький Принц - в одном из писем к матери Сент-Экзюпери признался: “Мне ненавистны люди, пишущие ради забавы, ищущие эффектов. Надо иметь что сказать”. Ему, романтику неба, не чуравшемуся земных радостей, любившему, по свидетельству друзей, “писать, говорить, петь, играть, докапываться до сути вещей, есть, обращать на себя внимание, ухаживать за женщинами”, человеку проницательного ума со своими достоинствами и недостатками, но всегда стоявшему на защите общечеловеческих ценностей, было “что сказать”. И он это сделал: написал сказку “Маленький принц”, о самом важном в этой жизни, жизни на планете Земля, все чаще такой неласковой, но любимой и единственной.\n" +
                        "Сказка, рассказанная Антуаном де Сент-Экзюпери, - мудрая и человечная, и автор ее не только писатель, но и философ. Просто и проникновенно говорит он о самом важном. О долге и верности. О дружбе и любви. О нетерпимости к злу.",
                "rus",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/33", ""));

        bookList.add(new Book("",
                "Магия утра",
                "Хэла Элрод",
                "Магия утра - некоторые книги меняют наше отношение к жизни. И лишь редкие из них меняют наш образ жизни и поведение. Книга Хэла Элрода делает и то, и другое – и быстрее, чем вы можете себе представить. Из нее вы узнаете, как первый час после пробуждения определяет успех всего дня и помогает раскрыть свой потенциал. Следуя советам автора, десятки тысяч человек улучшили свое здоровье, стали больше зарабатывать, научились фокусироваться на ключевых задачах и, главное, стали счастливее.\n" +
                        "Эта книга для всех, кто хочет изменить жизнь, начав с малого – с первого утреннего часа.\n" +
                        "На русском языке публикуется впервые.",
                "rus",
                100,
                "4,5", "",
                "https://t.me/ozindiDamytKitapkhana/32", ""));


    }

    public void filter(String text) {
//        normalState(btn_kz);
//        normalState(btn_rus);
        bookList.clear();
        if (text.isEmpty()) {
            bookList.addAll(bookListCopy);
            monthBookLayout.setVisibility(View.VISIBLE);
        } else {
            text = text.toLowerCase();
            for (Book item : bookListCopy) {

                if (item.getName().toLowerCase().contains(text) || item.getAuthor().toLowerCase().contains(text) ||
                        item.getName().toUpperCase().contains(text) || item.getAuthor().toUpperCase().contains(text)) {

                    bookList.add(item);
                }
            }
        }
        bookListAdapter.notifyDataSetChanged();
    }

    public void btnPressedState(Button btn){
        btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btn.setTextColor(getResources().getColor(R.color.white));
    }

    public void normalState(Button btn){
        btn.setBackgroundColor(getResources().getColor(R.color.white));
        btn.setTextColor(getResources().getColor(R.color.colorAccent));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                monthBookLayout.setVisibility(View.GONE);
                filter(s);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                filterDialog();
//                Toast.makeText(getActivity(), "Filter call log", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
