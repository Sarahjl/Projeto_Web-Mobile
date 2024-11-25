const express = require('express')
const app = express()
const path = require('path');

const { initializeApp, cert } = require('firebase-admin/app');
const { getFirestore } = require('firebase-admin/firestore');
const firebaseAdmin = require('firebase-admin');

const serviceAccount = require('./key.json')

// Inicialize o aplicativo Firebase com o service account
initializeApp({
  credential: cert(serviceAccount),
});

const db = getFirestore();

const PORT = 3000

app.listen(PORT, () => {
    console.log('http://localhost:' + PORT);
})

// Configuração do handlebars
const exphbs = require('express-handlebars');
app.engine('handlebars', exphbs.engine({
    defaultLayout: "main",
    layoutsDir: path.join(__dirname, "../public", "views", "layouts"),
    partialsDir: path.join(__dirname, "../public", "views", "layouts"),
    helpers: {
        eq: function(v1, v2){
            return v1 == v2
        }
    }

}));
app.set('view engine', 'handlebars');
app.set('views', path.join(__dirname, "../public/views"));

// Servindo arquivos estáticos
app.use(express.static('public'));
app.use(express.urlencoded({ extended: true }));

app.get('/', (req, res)=>{
    res.render('home', {title: "Home"})
})

app.get('/cadastrar', (req, res)=>{
    res.render('registrar', { title: "Agendar" })
})

app.get('/login', (req, res)=>{
    res.render('login', { title: "Login"})
})

app.post('/registrar', async(req, res)=>{
    const { email, password } = req.body;
    if (!email || !password) {
        return res.status(400).json({ error: 'Email e senha são obrigatórios.' });
    }

    try {
        await firebaseAdmin.auth().createUser({
            email: email,
            password: password,
        });

        return res.status(201).json({ message: 'Conta criada com sucesso!', email, password });
    } catch (error) {
        return res.status(500).json({ error: error.message });
    }
})

app.post('/cadastrar', async (req, res)=>{
    let { nome, tipo_pet, raca, porte, idade, sexo, servico, data_tosa, hora_tosa }  = req.body
    let data = { nome, tipo_pet, raca, porte, idade, sexo, servico, data_tosa, hora_tosa };

    if (!nome || !tipo_pet || !raca || !porte || !idade, !sexo, !servico, !data_tosa, !hora_tosa) 
        return res.redirect('/consultar?mensagemErro=Todos os campos são obrigatorios')

    let docRef = await db.collection('agendamentos').add(data);

    let id = docRef.id
    let doc = await db.collection('agendamentos').doc(id).get()
    let agendamento = doc.data()
    agendamento.id = doc.id

    return res.redirect('/consultar?mensagemSucesso=Agendamento Cadastrado')
})

app.get('/consultar', async (req, res)=>{
    let mensagemSucesso = req.query.mensagemSucesso;
    let mensagemErro = req.query.mensagemErro;

    await db.collection('agendamentos').get().then(queryDocumentSnapshot =>{
        let agendamentos = [];
        queryDocumentSnapshot.forEach(documentSnapshot =>{
            let a = documentSnapshot.data()
            a.id = documentSnapshot.id
            agendamentos.push(a)
        })
        return res.render('consultar', { agendamentos, mensagemSucesso, mensagemErro, title: "Consultar" })
    }).catch(error =>{
        return res.send(error)
    })
})

app.get('/editar/:id', async (req, res)=>{
    let id = req.params.id;
    if(!id)
        return res.status(400).json({code: 400, message: "id não informado"})

    await db.collection('agendamentos').doc(id).get().then(documentSnapshot =>{
        if(!documentSnapshot.exists)
            return res.status(404).json({code: 404, message: "Agendamento não encontrado"})

        let agendamento = documentSnapshot.data()
        agendamento.id = documentSnapshot.id

        res.render('registrar', {agendamento, title: "Editar"})
    }).catch(error =>{
        return res.redirect(`/consultar?mensagemErro=${error}`)
    })
})

app.post('/editar', async (req, res)=>{
    let { nome, tipo_pet, raca, porte, idade, sexo, servico, data_tosa, hora_tosa, id }  = req.body
    let data = { nome, tipo_pet, raca, porte, idade, sexo, servico, data_tosa, hora_tosa, id };


    if (!nome || !tipo_pet || !raca || !porte || !idade, !sexo, !servico, !data_tosa, !hora_tosa) 
        return res.redirect(`/consultar?mensagemErro=Todos os campos são obrigatórios`)

    let docRef = db.collection('agendamentos').doc(id)
    let doc = await docRef.get();

    if(!doc.exists)
        return res.redirect(`/consultar?mensagemErro=Agendamento não encontrado`)

    docRef.update(data).then(()=>{
        let agendamento = doc.data();
        agendamento.id = doc.id;

        return res.redirect('/consultar?mensagemSucesso=Agendamento Atualizado')
    }).catch(error =>{
        return res.redirect(`/consultar?mensagemErro=${error}`)
    })
})


app.get('/apagar/:id', async(req, res) =>{
    try {
        let id = req.params.id;

        if(!id)
            return res.redirect(`/consultar?mensagemErro=id não informado`)

        let docRef = db.collection('agendamentos').doc(id);
        let doc = await docRef.get()

        if(!doc.exists)
            return res.redirect(`/consultar?mensagemErro=Agendamento não encontrado`)

        await docRef.delete()
        return res.redirect(`/consultar?mensagemSucesso=Agendamento apagado`)
    } catch (error) {
        return res.status(500).json({code: 500, message:`${error}`})
    }
})