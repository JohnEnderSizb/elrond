# Generated by Django 2.2.5 on 2019-09-08 13:32

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('main', '0005_auto_20190908_1506'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='profile',
            name='username',
        ),
        migrations.RemoveField(
            model_name='tweet',
            name='tweet_bg',
        ),
        migrations.AddField(
            model_name='tweet',
            name='tweet_id',
            field=models.TextField(default='default'),
            preserve_default=False,
        ),
    ]